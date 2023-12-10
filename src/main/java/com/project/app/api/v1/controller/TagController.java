package com.project.app.api.v1.controller;

import com.project.app.api.v1.entity.Tag;
import com.project.app.api.v1.service.TagService;
import com.project.app.exception.AlreadyExistsException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("${API_V1}")
public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }
    @GetMapping("/tags/download")
    public ResponseEntity<List<Tag>> getAllTags() {
        List<Tag> tags = tagService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(tags);
    }

    @GetMapping("/tag/download/{tagId}")
    public ResponseEntity<?> get(@PathVariable int tagId) {
        Tag tag;
        try {
            tag = tagService.get(tagId).orElseThrow();
        } catch (NoSuchElementException noSuchElementException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Provided tag does not exists.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(tag);
    }

    //
    @PostMapping("/tag/upload")
    public ResponseEntity<String> postTag(@RequestBody Tag tag) {
        try{
            tagService.saveSafe(tag);
        } catch (AlreadyExistsException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Tag already exists.");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something goes wrong.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Tag created successfully.");
    }

    //
    @PutMapping("/tag/edit")
    public ResponseEntity<String> patchTag(@RequestBody Tag tag) {
        try{
            tagService.patch(tag);
        } catch (AlreadyExistsException alreadyExistsException){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Tag already exists.");
        } catch (NoSuchElementException noSuchElementException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tag do not exist.");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something goes wrong.");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Tag updated successfully.");
    }

    //
    @DeleteMapping("/tag/delete/{tagId}")
    public ResponseEntity<String> deleteTag(@PathVariable int tagId) {
        try{
            tagService.delete(tagId);
        } catch (NoSuchElementException noSuchElementException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tag do not exist.");
        } catch (DataIntegrityViolationException dataIntegrityViolationException){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Tag is being used somewhere else.");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something goes wrong.");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Tag deleted successfully.");
    }
}
