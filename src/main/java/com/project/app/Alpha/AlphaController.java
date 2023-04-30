package com.project.app.Alpha;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@RestController
@RequestMapping("/alpha")
public class AlphaController {

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
    public String postAlphaLarge(@RequestBody MultipartFile multipartFile) throws IOException {

        FileManager fileManager = new FileManager();
        //fileManager.saveLargeFile(multipartFile);

        return multipartFile.getBytes().toString();
    }
}
