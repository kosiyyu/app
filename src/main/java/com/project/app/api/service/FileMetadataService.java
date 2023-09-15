package com.project.app.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.app.api.dto.MetadataFileDto;
import com.project.app.api.dto.MultipartFileTagDto;
import com.project.app.api.entity.Metadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.List;

@Service
public class FileMetadataService {

    @Value("${FILE_PATH}")
    private String filesPath;

    private final MetadataService metadataService;

    private final FileService fileService;

    public FileMetadataService(MetadataService metadataService, FileService fileService){
        this.metadataService = metadataService;
        this.fileService = fileService;
    }

    public void save(byte[] fileByteArray, String originalFilename) throws IOException {

        // SAVE METADATA TO DATABASE
        Metadata metadata = new Metadata(originalFilename, filesPath);
        metadata = metadataService.save(metadata);

        String extension = originalFilename.contains(".") ? originalFilename.substring(originalFilename.lastIndexOf(".")) : "";
        String newFilename = metadata.getId() + extension;

        // SAVE FILE TO DIRECTORY
        fileService.save(fileByteArray, newFilename);
    }

    public void saveRecord(MultipartFileTagDto multipartFileTagDto) throws IOException {

        // SAVE METADATA TO DATABASE
        Metadata metadata = new Metadata(
                multipartFileTagDto.multipartFile().getOriginalFilename(),
                filesPath,
                multipartFileTagDto.tags()
                );
        metadata = metadataService.save(metadata);

        // SAVE FILE TO DIRECTORY
        fileService.save(multipartFileTagDto.multipartFile(), Integer.toString(metadata.getId()));
    }

    public void saveRecordList(List<MultipartFileTagDto> multipartFileTagDtoList) throws IOException {

        // SAVE METADATA LIST TO DATABASE
        List<Metadata> metadataList = multipartFileTagDtoList.stream()
                .map(x -> new Metadata(x.multipartFile().getOriginalFilename(), filesPath, x.tags()))
                .toList();
        metadataList = metadataService.saveList(metadataList);

        List<String> stringList = metadataList.stream()
                .map(x -> Integer.toString(x.getId()))
                .toList();

        List<MultipartFile> multipartFileList = multipartFileTagDtoList.stream()
                .map(x -> x.multipartFile())
                .toList();

        // SAVE FILE LIST TO DIRECTORY
        fileService.saveList(multipartFileList, stringList);
    }

    public String get(int id) throws IOException {
        String json;
        Metadata metadata = metadataService.find(id).orElseThrow();
        String path = metadata.getPath()
                + metadata.getId()
                + (metadata.getOriginalFilename().contains(".") ? metadata.getOriginalFilename().substring(metadata.getOriginalFilename().lastIndexOf(".")) : "");

        String encodedByteArray = Base64.getEncoder().encodeToString(Files.readAllBytes(Path.of(path)));

        ObjectMapper objectMapper = new ObjectMapper();
        MetadataFileDto metadataFileDto = new MetadataFileDto(metadata.getId(), metadata.getOriginalFilename(), metadata.getTags(), encodedByteArray);
        json = objectMapper.writeValueAsString(metadataFileDto);
        return json;
    }
}
