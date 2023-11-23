package com.project.app.api.service;

import com.project.app.api.dto.MultipartDto;
import com.project.app.api.entity.Metadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

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

    public Metadata save(byte[] fileByteArray, String originalFilename) throws IOException {
        // SAVE METADATA TO DATABASE
        Metadata metadata = new Metadata(originalFilename, filesPath);
        metadata = metadataService.save(metadata);
        // EXTRACT STRINGS
        String extension = originalFilename.contains(".") ? originalFilename.substring(originalFilename.lastIndexOf(".")) : "";
        String newFilename = metadata.getId() + extension;
        // SAVE FILE TO DIRECTORY
        fileService.save(fileByteArray, newFilename);
        return metadata;
    }

    public Metadata update(Metadata metadata, byte[] fileByteArray, String originalFilename) throws NoSuchElementException, IOException {
        // EXTRACT STRINGS
        String newExtension = originalFilename.contains(".") ? originalFilename.substring(originalFilename.lastIndexOf(".")) : "";
        String newFilename = metadata.getId() + newExtension;

        String oldExtension = metadata.getOriginalFilename().contains(".") ? metadata.getOriginalFilename().substring(metadata.getOriginalFilename().lastIndexOf(".")) : "";
        String oldFilename = metadata.getId() + oldExtension;
        // UPDATE FILE TO DIRECTORY
        fileService.update(oldFilename, fileByteArray, newFilename);
        // UPDATE METADATA
        metadata.setOriginalFilename(originalFilename);
        metadata.setPath(filesPath);
        return metadataService.save(metadata);
    }

    public void deleteFile(int id) throws NoSuchElementException, IOException {
        Metadata metadata = metadataService.findById(id).orElseThrow();

        String oldExtension = metadata.getOriginalFilename().contains(".") ? metadata.getOriginalFilename().substring(metadata.getOriginalFilename().lastIndexOf(".")) : "";
        String oldFilename = metadata.getId() + oldExtension;

        // DELETE FILE
        fileService.delete(oldFilename);
    }

    public MultipartDto getMultipartDto(int metadataId) throws IOException {

        Metadata metadata = getMetadata(metadataId);

        String extension = metadata.getOriginalFilename().contains(".") ? metadata.getOriginalFilename().substring(metadata.getOriginalFilename().lastIndexOf(".")) : "";
        String fullFilename = metadata.getId() + extension;

        byte[] bytes = fileService.get(fullFilename);

        return new MultipartDto(fullFilename, bytes);
    }

    public Metadata getMetadata(int metadataId) {
        return metadataService.findById(metadataId).orElseThrow();
    }
}
