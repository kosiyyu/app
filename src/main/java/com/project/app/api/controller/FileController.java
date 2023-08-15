package com.project.app.api.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.project.app.api.dto.MetadataFileDto;
import com.project.app.api.entity.Metadata;
import com.project.app.api.entity.Tag;
import com.project.app.api.dto.MultipartFileTagDto;
import com.project.app.api.service.FileMetadataService;
import com.project.app.api.service.MetadataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@RestController
@RequestMapping("${API_V1}")
public class FileController {

    private final FileMetadataService fileMetadataService;

    private final MetadataService metadataService;

    public FileController(FileMetadataService fileMetadataService, MetadataService metadataService) {
        this.fileMetadataService = fileMetadataService;
        this.metadataService = metadataService;
    }

    @PostMapping(value = "/file/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity postFile(@RequestParam("document") MultipartFile multipartFile, @RequestParam("tags") String json) throws IOException {

        List<Tag> tags = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode jsonNode = objectMapper.readTree(json);
        if (jsonNode.isArray()) {
            for (JsonNode tagNode : jsonNode) {
                String value = tagNode.get("value").asText();
                tags.add(new Tag(value));
            }
        }

        MultipartFileTagDto multipartFileTagDto = new MultipartFileTagDto(multipartFile, tags);
        fileMetadataService.saveRecord(multipartFileTagDto);

        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }

    @PostMapping(value = "/files/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity postFiles(@RequestParam("documents") List<MultipartFile> multipartFiles, @RequestBody List<List<Tag>> tags) throws IOException {

//        if(multipartFiles.size() != tags.size()){
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Invalid file and tag sizes.");
//        }
//
//        List<MultipartFileTagDto> multipartFileTagRecordList = IntStream.range(0, multipartFiles.size())
//                .mapToObj(x -> new MultipartFileTagDto(multipartFiles.get(x), tags.get(x)))
//                .collect(Collectors.toList());
//        fileMetadataService.saveRecordList(multipartFileTagRecordList);
//
//        return ResponseEntity.status(HttpStatus.CREATED).build();
        throw new RuntimeException("TO DO");
    }

    @GetMapping(value = "/file/download/{id}")
    public ResponseEntity<?> getFile(@PathVariable String id){
        try{
            String json = fileMetadataService.get(Integer.parseInt(id));
            return ResponseEntity.status(HttpStatus.OK).body(json);
        }
        catch (NumberFormatException numberFormatException){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Provided id is invalid.");
        }
        catch (NoSuchElementException noSuchElementException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File with provided id do not exist.");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("That's a bummer. Something is not right.");
        }
    }
}
