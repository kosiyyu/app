package com.project.app.Alpha.Controller;

import com.project.app.Alpha.Model.TestData;
import com.project.app.Alpha.Service.TestDataService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestDataController {
    private final TestDataService testDataService;

    public TestDataController(TestDataService testDataService){
        this.testDataService = testDataService;
    }

    @GetMapping
    public List<TestData> getAll(){
        return testDataService.findAll();
    }

    @PostMapping("post")
    public String getAll(@RequestBody TestData testData){
        return testDataService.add(testData) != null ? "Success" : "Failure";
    }
}
