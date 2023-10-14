package com.project.app.api.service;

import com.project.app.api.dto.CustomSearchDto;
import com.project.app.api.dto.SearchTokenDto;
import com.project.app.api.entity.Journal;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class QueryService {
    private final EntityManager entityManager;

    public QueryService(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    public CustomSearchDto query(SearchTokenDto searchTokenDto){
        int limit = Math.min(searchTokenDto.pageSize(), 100);
        int offset = searchTokenDto.pageIndex();
        boolean isDesc = searchTokenDto.isDescSort();

        if (offset < 0) {
            return new CustomSearchDto(0, offset, Collections.emptyList());
        }

        // WHERE CONDITION
        StringBuilder stringBuilder = new StringBuilder();
        List<String> whereArguments = searchTokenDto.whereArguments();
        if (!whereArguments.isEmpty()) {
            stringBuilder.append("WHERE ");
            for (int i = 0; i < whereArguments.size(); i++) {
                if (i > 0) {
                    stringBuilder.append(" OR ");
                }
                stringBuilder.append(" t.value ILIKE '%").append(whereArguments.get(i)).append("%' ")
                        .append("OR j.title1 ILIKE '%").append(whereArguments.get(i)).append("%' ")
                        .append("OR j.title2 ILIKE '%").append(whereArguments.get(i)).append("%' ");
            }
        }
        String whereCondition = stringBuilder.toString();
        //

        // ORDER BY CONDITION
        String orderByArgument = searchTokenDto.orderByArgument();
        String orderByCondition;
        switch(orderByArgument){
            case "id" -> orderByCondition = "order by id " + (isDesc ? "desc " : "asc ");
            case "title1" -> orderByCondition = "order by title1 " + (isDesc ? "desc " : "asc ");
            case "issn1" -> orderByCondition = "order by issn1 " + (isDesc ? "desc " : "asc ");
            case "eissn1" -> orderByCondition = "order by eissn1 " + (isDesc ? "desc " : "asc ");
            case "title2" -> orderByCondition = "order by title2 " + (isDesc ? "desc " : "asc ");
            case "issn2" -> orderByCondition = "order by issn2 " + (isDesc ? "desc " : "asc ");
            case "eissn2" -> orderByCondition = "order by eissn2 " + (isDesc ? "desc " : "asc ");
            default -> orderByCondition = "";
        }
        //

        String sqlCount =
                "SELECT COUNT(DISTINCT j.id) " +
                        "FROM journal j " +
                        "INNER JOIN journal_tag t_g ON j.id = t_g.journal_id " +
                        "INNER JOIN tag t ON t_g.tag_id = t.id " +
                        whereCondition;
        Query query = entityManager.createNativeQuery(sqlCount, Long.class);
        long count = (long) query.getSingleResult();
        long numberOfPages = (long)Math.ceil((double)count / limit);
        if(offset > numberOfPages || offset < 0) {
            return new CustomSearchDto(numberOfPages, offset, Collections.emptyList());
        }

        String sqlJournals =
            "SELECT DISTINCT j.id, j.title1, j.issn1, j.eissn1, j.title2, j.issn2, j.eissn2, j.points " +
                "FROM journal j " +
                "INNER JOIN journal_tag t_g ON j.id = t_g.journal_id " +
                "INNER JOIN tag t ON t_g.tag_id = t.id " +
                whereCondition +
                orderByCondition +
                "LIMIT " + limit + " " +
                "OFFSET " + offset;

        List<Journal> journals = entityManager.createNativeQuery(sqlJournals, Journal.class).getResultList();
        return new CustomSearchDto(numberOfPages,searchTokenDto.pageIndex(),journals);
    }
}
