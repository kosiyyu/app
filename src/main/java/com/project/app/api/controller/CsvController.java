package com.project.app.api.controller;

import com.project.app.api.service.CsvService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.UnsupportedMediaTypeStatusException;

import java.io.IOException;

@RestController
@RequestMapping("${API_V1}")
public class CsvController {
    private final CsvService csvService;

    public CsvController(CsvService csvService) {
        this.csvService = csvService;
    }

    @PostMapping(value = "csv/upload/old", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> postCsvOld(@RequestParam("csv") MultipartFile multipartFile){
        try{
            csvService.loadCsv(multipartFile.getBytes());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error" + e);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("success");
    }

    @PostMapping(value = "csv/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> postCsv(@RequestParam("csv") MultipartFile multipartFile) {
        try{
            csvService.extractCsvData(multipartFile.getBytes());
        } catch (UnsupportedMediaTypeStatusException unsupportedMediaTypeStatusException){
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("error" + unsupportedMediaTypeStatusException);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error" + e);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("success");
    }
}
