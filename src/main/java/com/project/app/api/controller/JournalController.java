package com.project.app.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.app.api.dto.CustomSearchDto;
import com.project.app.api.dto.SearchTokenDto;
import com.project.app.api.entity.Journal;
import com.project.app.api.service.JournalService;
import com.project.app.api.service.FileMetadataService;
import com.project.app.api.service.QueryService;
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

    @PostMapping("/journal/upload")
    public ResponseEntity<?> postJournal(@RequestParam("file") MultipartFile multipartFile, @RequestParam("jsonObject") String json) throws JsonProcessingException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Journal journal = objectMapper.readValue(json, Journal.class);
            if (multipartFile == null) {
                fileMetadataService.save(multipartFile.getBytes(), multipartFile.getOriginalFilename());
            }
            journalService.saveJournalWithUniqueTags(journal);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something goes wrong." + e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.OK).body("Journal created successfully.");
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
    public ResponseEntity<?> postJournalBundle(@RequestParam("file") MultipartFile multipartFile, @RequestParam("journalJson") String journalJson) throws IOException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Journal journal = objectMapper.readValue(journalJson, Journal.class);
            fileMetadataService.save(multipartFile.getBytes(), multipartFile.getOriginalFilename());
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

    @GetMapping("/journals/tokenized/download")
    public ResponseEntity<?> getQuery(@RequestBody SearchTokenDto searchTokenDto) {
        CustomSearchDto customSearchDto = journalService.customSearch(searchTokenDto);
        return ResponseEntity.status(HttpStatus.OK).body(customSearchDto);
    }

    @GetMapping("/journals/test/download")
    public ResponseEntity<?> aa() {
        var var = queryService.query("3d", "order by j.title1",3, 0, true);
        return ResponseEntity.status(HttpStatus.OK).body(var);
    }

    @GetMapping("/journals/count/download")
    public ResponseEntity<?> getCount() {
        long count = journalService.count();
        return ResponseEntity.status(HttpStatus.OK).body(count);
    }
}


