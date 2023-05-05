package com.project.app.Alpha.Service;

import com.project.app.Alpha.Model.TestData;
import com.project.app.Alpha.Repository.TestDataRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestDataService {
    private final TestDataRepository testDataRepository;

    public TestDataService(TestDataRepository testDataRepository){
        this.testDataRepository = testDataRepository;
    }

    public TestData add(TestData testData){
        return testDataRepository.save(testData);
    }

    public List<TestData> findAll(){
        return testDataRepository.findAll();
    }

    public TestData get(String fullFilename){
        return testDataRepository.findById(fullFilename).get();
    }
}
