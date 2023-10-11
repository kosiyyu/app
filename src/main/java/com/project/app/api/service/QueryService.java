package com.project.app.api.service;

import com.project.app.api.entity.Journal;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class QueryService {
    private final EntityManager entityManager;

    public QueryService(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    public List<Journal> query(String whereArgument, String orderByCondition, int limit, int offset, boolean isDesc){
        String sql =
            "SELECT DISTINCT j.id, j.title1, j.issn1, j.eissn1, j.title2, j.issn2, j.eissn2, j.points " +
                "FROM journal j " +
                "INNER JOIN journal_tag t_g ON j.id = t_g.journal_id " +
                "INNER JOIN tag t ON t_g.tag_id = t.id " +
                "WHERE t.value ILIKE '%" + whereArgument + "%' " +
                "OR j.title1 ILIKE '%" + whereArgument + "%' " +
                "OR j.title2 ILIKE '%" + whereArgument + "%' " +
                orderByCondition + " " + (isDesc ? "desc " : "asc ") +
                "LIMIT " + limit + " " +
                "OFFSET " + offset;
        List<Journal> journals = entityManager.createNativeQuery(sql, Journal.class).getResultList();
        return journals;
    }
}
