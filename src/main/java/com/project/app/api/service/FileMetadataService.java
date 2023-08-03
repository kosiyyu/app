package com.project.app.api.service;

import com.project.app.api.model.Metadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileMetadataService {

    @Value("${FILES_PATH}")
    private String filesPath;

    private final MetadataService metadataService;

    private final FileService fileService;

    public FileMetadataService(MetadataService metadataService, FileService fileService){
        this.metadataService = metadataService;
        this.fileService = fileService;
    }

    public void save(MultipartFile multipartFile) throws IOException {

        // SAVE METADATA TO DATABASE
        Metadata metadata = new Metadata(multipartFile.getOriginalFilename(), filesPath);
        metadataService.save(metadata);

        // SAVE FILE TO DIRECTORY
        fileService.save(multipartFile);
    }

    public void saveList(List<MultipartFile> multipartFileList) throws IOException {

        // SAVE METADATA LIST TO DATABASE
        List<Metadata> metadataList = multipartFileList.stream()
                .map(x -> new Metadata(x.getOriginalFilename(), filesPath))
                .toList();
        metadataService.saveList(metadataList);

        // SAVE FILE LIST TO DIRECTORY
        fileService.saveList(multipartFileList);
    }
}
