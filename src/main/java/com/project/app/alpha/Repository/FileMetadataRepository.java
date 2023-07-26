package com.project.app.alpha.Repository;

import com.project.app.alpha.Model.Metadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileMetadataRepository extends JpaRepository<Metadata, String> {
}
