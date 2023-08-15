package com.project.app.api.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.project.app.api.entity.Metadata;
import com.project.app.api.entity.Tag;
import com.project.app.api.model.MultipartFileTagRecord;
import com.project.app.api.service.FileMetadataService;
import com.project.app.api.service.MetadataService;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.lang.model.type.ReferenceType;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

        MultipartFileTagRecord multipartFileTagRecord = new MultipartFileTagRecord(multipartFile, tags);
        fileMetadataService.saveRecord(multipartFileTagRecord);

        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }

    @PostMapping(value = "/files/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity postFiles(@RequestParam("documents") List<MultipartFile> multipartFiles, @RequestBody List<List<Tag>> tags) throws IOException {

//        if(multipartFiles.size() != tags.size()){
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Invalid file and tag sizes.");
//        }
//
//        List<MultipartFileTagRecord> multipartFileTagRecordList = IntStream.range(0, multipartFiles.size())
//                .mapToObj(x -> new MultipartFileTagRecord(multipartFiles.get(x), tags.get(x)))
//                .collect(Collectors.toList());
//        fileMetadataService.saveRecordList(multipartFileTagRecordList);
//
//        return ResponseEntity.status(HttpStatus.CREATED).build();
        throw new RuntimeException("TO DO");
    }

    @GetMapping(value = "/file/download/{id}")
    @ResponseBody
    public ResponseEntity<?> getFile(@PathVariable String id){

        Metadata metadata;
        String json;
        try{
            metadata = metadataService.find(Integer.parseInt(id)).orElseThrow();
            String path = metadata.getPath()
                    + metadata.getId()
                    + (metadata.getOriginalFilename().contains(".") ? metadata.getOriginalFilename().substring(metadata.getOriginalFilename().lastIndexOf(".")) : "");

            byte[] fileByteArray = Files.readAllBytes(Path.of(path));

            String encodedByteArray = Base64.getEncoder().encodeToString(fileByteArray);

            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode objectNode = objectMapper.createObjectNode();

            objectNode.put("id", metadata.getId());
            objectNode.put("filename", metadata.getOriginalFilename());
            objectNode.put("encodedByteArray", encodedByteArray);

            json = objectMapper.writeValueAsString(objectNode);
            return ResponseEntity.status(HttpStatus.OK).body(json);
        }
        catch (NoSuchElementException noSuchElementException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File with provided id do not exist.");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("That's a bummer. Something is not right.");
        }
    }
}
