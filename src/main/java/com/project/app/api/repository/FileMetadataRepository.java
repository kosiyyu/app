package com.project.app.api.repository;

import com.project.app.api.entity.Metadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileMetadataRepository extends JpaRepository<Metadata, String> {
}
