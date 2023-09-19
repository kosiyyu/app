package com.project.app.api.service;

import com.project.app.api.entity.Metadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class FileMetadataService {
    private final MetadataService metadataService;
    private final FileService fileService;
    @Value("${FILE_PATH}")
    private String filesPath;

    public FileMetadataService(MetadataService metadataService, FileService fileService) {
        this.metadataService = metadataService;
        this.fileService = fileService;
    }

    public void save(byte[] fileByteArray, String originalFilename) throws IOException {
        // SAVE METADATA TO DATABASE
        Metadata metadata = new Metadata(originalFilename, filesPath);
        metadata = metadataService.save(metadata);
        // EXTRACT STRINGS
        String extension = originalFilename.contains(".") ? originalFilename.substring(originalFilename.lastIndexOf(".")) : "";
        String newFilename = metadata.getId() + extension;
        // SAVE FILE TO DIRECTORY
        fileService.save(fileByteArray, newFilename);
    }
}
