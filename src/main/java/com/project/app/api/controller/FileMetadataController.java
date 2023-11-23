package com.project.app.api.controller;

import com.project.app.api.dto.MultipartDto;
import com.project.app.api.service.FileMetadataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("${API_V1}")
public class FileMetadataController {
    private final FileMetadataService fileMetadataService;

    public FileMetadataController(FileMetadataService fileMetadataService) {
        this.fileMetadataService = fileMetadataService;
    }

    @GetMapping(value = "/metadata/download/{id}", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> getFileMetadata(@PathVariable int id) {
        MultipartDto multipartDto;
        try {
            multipartDto = fileMetadataService.getMultipartDto(id);
        } catch (IOException ioException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found.");
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something goes wrong.");
        }

        MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("filename", multipartDto.fullFilename());
        multiValueMap.add("byteArray", multipartDto.bytes());

        return ResponseEntity.status(HttpStatus.OK).body(multiValueMap);
    }
}
