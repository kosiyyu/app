package com.project.app.Alpha.Repository;

import com.project.app.Alpha.Model.Metadata;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MetadataRepository extends MongoRepository<Metadata, String> {
}
