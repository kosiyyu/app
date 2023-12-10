package com.project.app.api.v1.repository;

import com.project.app.api.v1.entity.Metadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetadataRepository extends JpaRepository<Metadata, Integer> {
}
