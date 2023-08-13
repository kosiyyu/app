package com.project.app.api.controller;

import com.project.app.api.entity.Tag;
import com.project.app.api.service.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("${API_V1}")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService){
        this.tagService = tagService;
    }
    @PostMapping("/tag/upload")
    public ResponseEntity<String> postTag(@RequestBody Tag tag) throws IOException {
        tagService.save(tag);
        return ResponseEntity.status(HttpStatus.CREATED).body("Tag created successfully.");
    }

    @GetMapping("/tags/download")
    public ResponseEntity<List<Tag>> getAllTags(){
        List<Tag> tags = tagService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(tags);
    }

    @GetMapping("/tag/download/{id}")
    public ResponseEntity<?> get(@PathVariable String id){
        Tag tag;
        try{
            tag = tagService.get(Integer.valueOf(id)).orElseThrow();
        }
        catch (NumberFormatException numberFormatException){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Provided id must be a number.");
        }
        catch (NoSuchElementException noSuchElementException){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Provided tag does not exists.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(tag);
    }
}
