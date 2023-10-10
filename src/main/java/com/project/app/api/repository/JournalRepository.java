package com.project.app.api.repository;

import com.project.app.api.entity.Journal;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JournalRepository extends JpaRepository<Journal, Integer> {
    @Query(
            """
                SELECT a FROM Journal a
                WHERE
                    CASE
                        WHEN :whereCondition = 'title_like' THEN (a.title1 LIKE CONCAT('%', :searchText, '%') OR a.title2 LIKE CONCAT('%', :searchText, '%'))
                        WHEN :whereCondition = 'title_equal' THEN (a.title1 = :searchText OR a.title2 = :searchText)
                        ELSE TRUE
                    END
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
                ASC
            """
    )
    List<Journal> customSearchAsc(
            @Param("searchText") String searchText,
            @Param("whereCondition") String whereCondition,
            @Param("orderByCondition") String orderByCondition,
            Pageable pageable
    );

    @Query(
            """
                SELECT a FROM Journal a
                WHERE
                    CASE
                        WHEN :whereCondition = 'title_like' THEN (a.title1 LIKE CONCAT('%', :searchText, '%') OR a.title2 LIKE CONCAT('%', :searchText, '%'))
                        WHEN :whereCondition = 'title_equal' THEN (a.title1 = :searchText OR a.title2 = :searchText)
                        ELSE TRUE
                    END
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
                DESC
            """
    )
    List<Journal> customSearchDesc(
            @Param("searchText") String searchText,
            @Param("whereCondition") String whereCondition,
            @Param("orderByCondition") String orderByCondition,
            Pageable pageable
    );

    @Query(nativeQuery = true,
            value = """
                select distinct j.id, j.title1, j.issn1, j.eissn1, j.title2, j.issn2, j.eissn2, j.points
                from journal j
                inner join journal_tag t_g on j.id = t_g.journal_id
                inner join tag t on t_g.tag_id = t.id
                where t.value ilike concat('%', ?1, '%') or j.title1 ilike concat('%', ?1, '%') or j.title2 ilike concat('%', ?1, '%')
                limit ?2
                offset ?3
                    """
    )
    List<Journal> query(String whereCondition, int _limit, int _offset);
}


