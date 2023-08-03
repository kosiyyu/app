package com.project.app.api.controller;

import com.project.app.api.model.Tag;
import com.project.app.api.service.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/alpha")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService){
        this.tagService = tagService;
    }
    @PostMapping("/post/tag")
    public ResponseEntity<String> postFile(@RequestBody Tag tag) throws IOException {
        tagService.save(tag);
        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }
}
