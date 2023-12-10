package com.project.app.api.v1.controller;

import com.project.app.api.v1.dto.FileContentDto;
import com.project.app.api.v1.service.FileMetadataService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("${API_V1}")
public class FileMetadataController {
    private final FileMetadataService fileMetadataService;

    public FileMetadataController(FileMetadataService fileMetadataService) {
        this.fileMetadataService = fileMetadataService;
    }

    @GetMapping(value = "/filemetadata/download/{metadataId}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<?> getFileMetadata(@PathVariable int metadataId) {
        FileContentDto filePackage;
        try {
            filePackage = fileMetadataService.getFile(metadataId);
        } catch (NoSuchElementException noSuchElementException) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Metadata not found.");
        } catch (IOException ioException) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("File not found.");
        } catch (Exception exception) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Something went wrong.");
        }

        ContentDisposition contentDisposition = ContentDisposition
                .builder("inline")
                .filename(filePackage.fullFilename())
                .build();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        httpHeaders.setContentDisposition(contentDisposition);

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(httpHeaders)
                .body(filePackage.bytes());
    }
}
