package com.project.app.api.repository;

import com.project.app.api.entity.Article;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {
    @Query(
            """
                SELECT a FROM Article a
                WHERE
                    CASE
                        WHEN :whereCondition = 'title' THEN (a.title1 LIKE %:searchText%)
                        WHEN :whereCondition = 'issn_like' THEN (a.eissn1 LIKE %:searchText%)
                        WHEN :whereCondition = 'eissn_equal' THEN (a.title2 = :searchText)
                        ELSE TRUE
                    END = TRUE
                ORDER BY
                    CASE
                        WHEN :orderByCondition = 'title1' THEN a.title1
                        WHEN :orderByCondition = 'issn1' THEN a.issn1
                        WHEN :orderByCondition = 'eissn1' THEN a.eissn2
                        WHEN :orderByCondition = 'title2' THEN a.title2
                        WHEN :orderByCondition = 'issn2' THEN a.issn2
                        WHEN :orderByCondition = 'eissn2' THEN a.eissn2
                        ELSE CAST(a.id AS string)
                    END
            """
    )
    public List<Article> customSearch(
            @Param("searchText") String searchText,
            @Param("whereCondition") String whereCondition,
            @Param("orderByCondition") String orderByCondition,
            Pageable pageable
    );
}


