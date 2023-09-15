package com.project.app.api.repository;

import com.project.app.api.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
    boolean existsByValue(String value);

    Optional<Tag> findByValue(String value);

    Optional<Tag> findFirstByValue(String value);
}
