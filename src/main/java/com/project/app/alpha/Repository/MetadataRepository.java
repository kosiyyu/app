package com.project.app.alpha.Repository;

import com.project.app.alpha.Model.Metadata;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetadataRepository extends MongoRepository<Metadata, String> {
}
