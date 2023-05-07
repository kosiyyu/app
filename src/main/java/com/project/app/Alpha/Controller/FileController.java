package com.project.app.Alpha.Controller;

import com.project.app.Alpha.Service.FileMetadataService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/alpha")
public class FileController {

    private final FileMetadataService fileMetadataService;

    public FileController(FileMetadataService fileMetadataService) {
        this.fileMetadataService = fileMetadataService;
    }

    @PostMapping("/large")
    public String postAlphaLarge(@RequestParam("document") MultipartFile multipartFile) throws IOException {
        return fileMetadataService.save(multipartFile);
    }
}
