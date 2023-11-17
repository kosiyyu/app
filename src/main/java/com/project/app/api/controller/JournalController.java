package com.project.app.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.app.api.dto.CustomSearchDto;
import com.project.app.api.dto.SearchTokenDto;
import com.project.app.api.entity.Journal;
import com.project.app.api.entity.Metadata;
import com.project.app.api.service.JournalService;
import com.project.app.api.service.FileMetadataService;
import com.project.app.api.service.QueryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
                Metadata metadata = fileMetadataService.save(multipartFile.getBytes(), multipartFile.getOriginalFilename());
                journal.setMetadata(metadata);
            }
            journalService.saveJournalWithUniqueTags(journal);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something goes wrong.");
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
        CustomSearchDto customSearchDto;
        try {
            customSearchDto = queryService.query(searchTokenDto);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something goes wrong.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(customSearchDto);
    }

    @PatchMapping(value = "/journal/edit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> patchJournal(@RequestParam(name = "file", required = false) MultipartFile multipartFile, @RequestParam("journalJson") String journalJson){
        Journal journal;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            journal = objectMapper.readValue(journalJson, Journal.class);
            // update metadata
            if(multipartFile != null){
                if(journal.getMetadata() != null){
                    // edit current file (delete old file, and create new one)
                    Metadata metadata = fileMetadataService.update(journal.getMetadata() ,multipartFile.getBytes(), multipartFile.getOriginalFilename());
                    journal.setMetadata(metadata);
                }
                else {
                    // add new file
                    Metadata metadata = fileMetadataService.save(multipartFile.getBytes(), multipartFile.getOriginalFilename());
                    journal.setMetadata(metadata);
                }
            }
            journalService.patch(journal);
        } catch (NoSuchElementException noSuchElementException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Journal do not exist.");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something goes wrong.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(journal);
    }

    @DeleteMapping ("/journal/delete/{journalId}")
    public ResponseEntity<?> deleteJournal(@PathVariable int journalId){
        try {
            int metadataId = 0;
            if(journalService.getMetadataId(journalId).isPresent()){
                metadataId = journalService.getMetadataId(journalId).get();
            }
            fileMetadataService.deleteFile(metadataId);
            journalService.delete(journalId);
        } catch (NoSuchElementException noSuchElementException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Journal do not exist.");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something goes wrong.");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Journal deleted successfully.");
    }
}


