package com.project.app.api.controller;

import com.project.app.api.service.CsvService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/alpha")
public class CsvController {

    private final CsvService csvService;

    public CsvController(CsvService csvService){
        this.csvService = csvService;
    }

    @PostMapping("post/csv")
    public ResponseEntity<String> postCsv(@RequestParam("csv") MultipartFile multipartFile) throws IOException {
        csvService.getDataFromCsv(multipartFile.getBytes());
        return ResponseEntity.status(HttpStatus.CREATED).body("success");
    }
}
