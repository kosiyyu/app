package com.project.app.api.controller;

import com.project.app.api.service.FileMetadataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${API_V1}")
public class FileMetadataController {
    private final FileMetadataService fileMetadataService;

    public FileMetadataController(FileMetadataService fileMetadataService) {
        this.fileMetadataService = fileMetadataService;
    }

    @GetMapping(value = "/metadata/download/{id}", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> getFileMetadata(@RequestParam int id){
        // return multipart
        // key = metadata value = metadata
        // key = file value = *file content
        // * if exists
        throw new RuntimeException("TODO");
    }
}
