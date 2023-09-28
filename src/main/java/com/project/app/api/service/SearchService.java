package com.project.app.api.service;

import com.project.app.api.dto.TokenSearchDto;
import com.project.app.api.entity.Article;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
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

    public List<Article> search(TokenSearchDto tokenSearchDto) {
        int pageNumber = 0; // it's a first page id
        if(tokenSearchDto.pageNumber() > 0){
            pageNumber = tokenSearchDto.pageNumber();
        }

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Article> query = criteriaBuilder.createQuery(Article.class);
        Root<Article> article = query.from(Article.class);

        Expression<String> sortingField = article.get(tokenSearchDto.sortingField());

        Expression<String> title1 = article.get("title1");
        Expression<String> issn1 = article.get("issn1");
        Expression<String> eissn1 = article.get("eissn1");
        Expression<String> title2 = article.get("title2");
        Expression<String> issn2 = article.get("issn2");
        Expression<String> eissn2 = article.get("eissn2");

        Predicate matchCondition = null;
        if (tokenSearchDto.match() != null && tokenSearchDto.comparison() != null) {
            Expression<String> matchField = criteriaBuilder.concat(
                criteriaBuilder.concat(
                    criteriaBuilder.concat(
                        criteriaBuilder.concat(title1, " "),
                        criteriaBuilder.concat(issn1, " ")
                    ),
                    criteriaBuilder.concat(
                        criteriaBuilder.concat(eissn1, " "),
                        criteriaBuilder.concat(title2, " ")
                    )
                ), criteriaBuilder.concat(
                        criteriaBuilder.concat(issn2, " "),
                        criteriaBuilder.concat(eissn2, " ")
                )
            );
            switch (tokenSearchDto.comparison()) {
                case like -> matchCondition = criteriaBuilder.like(matchField, "%" + tokenSearchDto.match() + "%");
                case equal -> matchCondition = criteriaBuilder.equal(matchField, tokenSearchDto.match());
            }
        }

        Predicate numericCondition = null;
        if (tokenSearchDto.number() != null && tokenSearchDto.operator() != null) {
            Expression<Integer> pointsField = article.get("points");
            switch (tokenSearchDto.operator()) {
                case equal -> numericCondition = criteriaBuilder.equal(pointsField, tokenSearchDto.number());
                case graterThan -> numericCondition = criteriaBuilder.greaterThan(pointsField, tokenSearchDto.number());
                case graterOrEqualTo -> numericCondition = criteriaBuilder.greaterThanOrEqualTo(pointsField, tokenSearchDto.number());
                case lesserThan -> numericCondition = criteriaBuilder.lessThan(pointsField, tokenSearchDto.number());
                case lesserThanOrEqualTo -> numericCondition = criteriaBuilder.lessThanOrEqualTo(pointsField, tokenSearchDto.number());
            }
        }

        Predicate finalCondition = null;
        if (matchCondition != null && numericCondition != null) {
            finalCondition = criteriaBuilder.and(matchCondition, numericCondition);
        }
        else if (matchCondition == null && numericCondition != null) {
            finalCondition = numericCondition;
        }
        else if (matchCondition != null && numericCondition == null) {
            finalCondition = matchCondition;
        }
        else {
            finalCondition = criteriaBuilder.and();
        }

        if (finalCondition != null) {
            switch (tokenSearchDto.sortingStrategy()) {
                case asc -> query.orderBy(criteriaBuilder.asc(sortingField));
                case desc -> query.orderBy(criteriaBuilder.desc(sortingField));
            }
        } else {
            throw new RuntimeException("Invalid query");
        }

        TypedQuery<Article> articleTypedQuery = entityManager.createQuery(query.select(article).where(finalCondition));

        final int max = 5;
        articleTypedQuery.setMaxResults(max);
        articleTypedQuery.setFirstResult(max * pageNumber);

        return articleTypedQuery.getResultList();
    }
}