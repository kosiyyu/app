package com.project.app.Alpha.Repository;

import com.project.app.Alpha.Model.TestData;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TestDataRepository extends MongoRepository<TestData, String> {
}
