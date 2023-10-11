package com.project.app.api.service;

import com.project.app.api.dto.CustomSearchDto;
import com.project.app.api.dto.SearchTokenDto;
import com.project.app.api.entity.Journal;
import jakarta.persistence.EntityManager;
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
        String whereArgument = searchTokenDto.whereCondition();
        String orderByArgument = searchTokenDto.orderByCondition();
        int limit = searchTokenDto.pageSize();
        int offset = searchTokenDto.pageIndex();
        boolean isDesc = searchTokenDto.isDescSort();

        String orderBySql;
        switch(orderByArgument){
            case "id" -> orderBySql = "order by id " + (isDesc ? "desc " : "asc ");
            case "title1" -> orderBySql = "order by title1 " + (isDesc ? "desc " : "asc ");
            case "issn1" -> orderBySql = "order by issn1 " + (isDesc ? "desc " : "asc ");
            case "eissn1" -> orderBySql = "order by eissn1 " + (isDesc ? "desc " : "asc ");
            case "title2" -> orderBySql = "order by title2 " + (isDesc ? "desc " : "asc ");
            case "issn2" -> orderBySql = "order by issn2 " + (isDesc ? "desc " : "asc ");
            case "eissn2" -> orderBySql = "order by eissn2 " + (isDesc ? "desc " : "asc ");
            default -> orderBySql = "";
        }

        String sqlCount =
                "SELECT DISTINCT count(*) " +
                        "FROM journal j " +
                        "INNER JOIN journal_tag t_g ON j.id = t_g.journal_id " +
                        "INNER JOIN tag t ON t_g.tag_id = t.id " +
                        "WHERE t.value ILIKE '%" + whereArgument + "%' " +
                        "OR j.title1 ILIKE '%" + whereArgument + "%' " +
                        "OR j.title2 ILIKE '%" + whereArgument + "%' " +
                        orderBySql +
                        "LIMIT " + limit + " " +
                        "OFFSET " + offset;
        long count = entityManager.createNativeQuery(sqlCount).getFirstResult();
        long numberOfPages = (long)Math.ceil((double)count / limit);
        if(offset > numberOfPages || offset < 0) {
            return new CustomSearchDto(numberOfPages, offset, Collections.emptyList());
        }

        String sqlJournals =
            "SELECT DISTINCT j.id, j.title1, j.issn1, j.eissn1, j.title2, j.issn2, j.eissn2, j.points " +
                "FROM journal j " +
                "INNER JOIN journal_tag t_g ON j.id = t_g.journal_id " +
                "INNER JOIN tag t ON t_g.tag_id = t.id " +
                "WHERE t.value ILIKE '%" + whereArgument + "%' " +
                "OR j.title1 ILIKE '%" + whereArgument + "%' " +
                "OR j.title2 ILIKE '%" + whereArgument + "%' " +
                orderBySql +
                "LIMIT " + limit + " " +
                "OFFSET " + offset;
        List<Journal> journals = entityManager.createNativeQuery(sqlJournals, Journal.class).getResultList();
        return new CustomSearchDto(numberOfPages,searchTokenDto.pageIndex(),journals);
    }
}
