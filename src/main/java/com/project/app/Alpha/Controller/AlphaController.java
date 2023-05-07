package com.project.app.Alpha.Controller;

import com.project.app.Alpha.Model.FileDto;
import com.project.app.Alpha.Service.FileManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
    public String postAlphaLarge(@RequestParam("document") MultipartFile multipartFile) throws IOException {

        FileManager fileManager = new FileManager();

        //System.out.println(multipartFile.getName()  + " " + multipartFile.getOriginalFilename());

        fileManager.saveLargeFile(multipartFile);

        return multipartFile.getBytes().toString();
    }
}
