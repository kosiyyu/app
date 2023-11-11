package com.project.app.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.app.api.dto.CustomSearchDto;
import com.project.app.api.dto.SearchTokenDto;
import com.project.app.api.entity.Journal;
import com.project.app.api.service.JournalService;
import com.project.app.api.service.FileMetadataService;
import com.project.app.api.service.QueryService;
import com.project.app.tools.AlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("${API_V1}")
public class JournalController {
    private final JournalService journalService;
    private final FileMetadataService fileMetadataService;
    private final QueryService queryService;

    public JournalController(JournalService journalService, FileMetadataService fileMetadataService, QueryService queryService) {
        this.journalService = journalService;
        this.fileMetadataService = fileMetadataService;
        this.queryService = queryService;
    }

    @GetMapping("/journal/download/{id}")
    public ResponseEntity<?> getJournal(@PathVariable String id) {
        Journal journal;
        try {
            journal = journalService.get(Integer.parseInt(id)).orElseThrow();
        } catch (NumberFormatException numberFormatException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Provided id must be a number.");
        } catch (NoSuchElementException noSuchElementException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Journal does not exists.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something goes wrong.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(journal);
    }

    @PostMapping(value = "/journal/bundle/upload/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> postJournalBundle(@RequestParam(name = "file", required = false) MultipartFile multipartFile, @RequestParam("journalJson") String journalJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Journal journal = objectMapper.readValue(journalJson, Journal.class);
            if(multipartFile != null){
                fileMetadataService.save(multipartFile.getBytes(), multipartFile.getOriginalFilename());
            }
            journalService.saveJournalWithUniqueTags(journal);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something goes wrong." + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("Journal created successfully.");
    }

    @GetMapping("/journals/download")
    public ResponseEntity<?> getJournals() {
        List<Journal> journals = journalService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(journals);
    }

    @PostMapping("/journals/tokenized/download")
    public ResponseEntity<?> getJournalsTokenized(@RequestBody SearchTokenDto searchTokenDto) {
        CustomSearchDto customSearchDto = queryService.query(searchTokenDto);
        return ResponseEntity.status(HttpStatus.OK).body(customSearchDto);
    }

    @PatchMapping("/journals/edit")
    public ResponseEntity<?> patchJournal(@RequestBody Journal journal){
        try {
            journalService.patch(journal);
        } catch (NoSuchElementException noSuchElementException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Journal do not exist.");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something goes wrong.");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Journal updated successfully.");
    }

    @DeleteMapping ("/journals/delete/{id}")
    public ResponseEntity<?> deleteJournal(@PathVariable int id){
        try {
            journalService.delete(id);
        } catch (NoSuchElementException noSuchElementException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Journal do not exist.");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something goes wrong.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}


