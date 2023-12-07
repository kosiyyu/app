package com.project.app.api.controller;

import com.project.app.api.entity.Tag;
import com.project.app.api.service.TagService;
import com.project.app.tools.AlreadyExistsException;
import org.springframework.dao.DataIntegrityViolationException;
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

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }
    @GetMapping("/tags/download")
    public ResponseEntity<List<Tag>> getAllTags() {
        List<Tag> tags = tagService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(tags);
    }

    @GetMapping("/tag/download/{id}")
    public ResponseEntity<?> get(@PathVariable String id) {
        Tag tag;
        try {
            tag = tagService.get(Integer.valueOf(id)).orElseThrow();
        } catch (NumberFormatException numberFormatException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Provided id must be a number.");
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
    @DeleteMapping("/tag/delete/{id}")
    public ResponseEntity<String> deleteTag(@PathVariable int id) {
        try{
            tagService.delete(id);
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
