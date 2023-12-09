package com.project.app.api.v1.repository;

import com.project.app.api.v1.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
    boolean existsByValue(String value);

    Optional<Tag> findFirstByValue(String value);
}
