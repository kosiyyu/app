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
@RequestMapping("${API_V1}")
public class CsvController {
    private final CsvService csvService;

    public CsvController(CsvService csvService) {
        this.csvService = csvService;
    }

    @PostMapping("csv/upload")
    public ResponseEntity<String> postCsv(@RequestParam("csv") MultipartFile multipartFile) throws IOException {
        try{
            csvService.loadCsv(multipartFile.getBytes());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error" + e);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("success");
    }

    @PostMapping("csv/upload2")
    public ResponseEntity<String> postCsv2(@RequestParam("csv") MultipartFile multipartFile) throws IOException {
        try{
            csvService.loadCsv2(multipartFile.getBytes());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error" + e);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("success");
    }
}
