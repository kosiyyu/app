package com.project.app.api.controller;

import com.project.app.api.service.FileMetadataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/alpha")
public class FileController {

    private final FileMetadataService fileMetadataService;

    public FileController(FileMetadataService fileMetadataService) {
        this.fileMetadataService = fileMetadataService;
    }

    @PostMapping("/post/file")
    public ResponseEntity<String> postFile(@RequestParam("document") MultipartFile multipartFile) throws IOException {
        fileMetadataService.save(multipartFile);
        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }

    @PostMapping("/post/files")
    public ResponseEntity postFiles(@RequestParam("documents") List<MultipartFile> multipartFiles) throws IOException {
        fileMetadataService.saveList(multipartFiles);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
