package com.project.app.Alpha.Controller;

import com.project.app.Alpha.Model.FileDto;
import com.project.app.Alpha.Model.TestData;
import com.project.app.Alpha.Service.FileManager;
import com.project.app.Alpha.Service.TestDataService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/alpha")
public class AlphaController {

    private final TestDataService testDataService;

    private final FileManager fileManager;

    public AlphaController(TestDataService testDataService, FileManager fileManager){
        this.testDataService = testDataService;
        this.fileManager = fileManager;
    }

    @GetMapping
    public String getAlpha() {
        return "Alpha";
    }

    @PostMapping
    public FileDto postAlpha(@RequestBody FileDto fileDto) throws IOException {

        FileManager fileManager = new FileManager();
        fileManager.saveFile(fileDto);

        return fileDto;
    }

    @PostMapping("/large")
    public String postAlphaLarge(@RequestParam("document") MultipartFile multipartFile) throws IOException {

        String originalFileName = multipartFile.getOriginalFilename();
        String extentionWithDot = originalFileName.substring(originalFileName.lastIndexOf('.') == -1 ? 0 : originalFileName.lastIndexOf('.'));

        //save in db
        TestData testData = new TestData(originalFileName,"src/main/resources/files/");
        testData = testDataService.add(testData);

        //save in dir
        fileManager.saveLargeFile(multipartFile, testData.getId() + extentionWithDot);


        return multipartFile.getBytes().toString();
    }
}
