package com.project.app.api.service;

import com.project.app.api.dto.SearchDto;
import com.project.app.api.entity.Article;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {
    @PersistenceContext
    private EntityManager entityManager;

    public SearchService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Article> search(SearchDto searchDto) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Article> query = criteriaBuilder.createQuery(Article.class);
        Root<Article> article = query.from(Article.class);

        Predicate matchCondition = null;
        if (searchDto.match() != null && searchDto.comparison() != null) {
            Expression<String> matchField = criteriaBuilder.concat(criteriaBuilder.concat(criteriaBuilder.concat(criteriaBuilder.concat(criteriaBuilder.concat(article.get("title1"), " "), article.get("issn1")), article.get("eissn1")), article.get("title2")), article.get("issn2"));
            switch (searchDto.comparison()) {
                case like -> matchCondition = criteriaBuilder.like(matchField, "%" + searchDto.match() + "%");
                case equal -> matchCondition = criteriaBuilder.equal(matchField, searchDto.match());
            }
        }

        Predicate numericCondition = null;
        if (searchDto.number() != null && searchDto.operator() != null) {
            Expression<Integer> pointsField = article.get("points");
            switch (searchDto.operator()) {
                case equal -> numericCondition = criteriaBuilder.equal(pointsField, searchDto.number());
                case graterThan -> numericCondition = criteriaBuilder.greaterThan(pointsField, searchDto.number());
                case graterOrEqualTo -> numericCondition = criteriaBuilder.greaterThanOrEqualTo(pointsField, searchDto.number());
                case lesserThan -> numericCondition = criteriaBuilder.lessThan(pointsField, searchDto.number());
                case lesserThanOrEqualTo -> numericCondition = criteriaBuilder.lessThanOrEqualTo(pointsField, searchDto.number());
            }
        }

        Predicate finalCondition = null;
        if (matchCondition != null && numericCondition != null) {
            finalCondition = criteriaBuilder.and(matchCondition, numericCondition);
        } else if (matchCondition == null && numericCondition != null) {
            finalCondition = numericCondition;
        } else if (matchCondition != null && numericCondition == null) {
            finalCondition = matchCondition;
        }

        if (finalCondition != null) {
            switch (searchDto.sortingStrategy()) {
                case asc -> query.orderBy(criteriaBuilder.asc(article.get("title1")));
                case desc -> query.orderBy(criteriaBuilder.desc(article.get("title1")));
            }
        } else {
            throw new RuntimeException("Invalid query");
        }
        return entityManager.createQuery(query.select(article).where(finalCondition)).getResultList();
    }
}