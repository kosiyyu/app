package com.project.app.api.v1.service;

import com.project.app.api.v1.dto.FileContentDto;
import com.project.app.api.v1.entity.Metadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
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

    public Metadata saveFileMetadata(byte[] fileByteArray, String originalFilename) throws IOException {
        Metadata metadata = new Metadata(originalFilename, filesPath);
        metadata = metadataService.saveMetadata(metadata);

        String extension =
                originalFilename
                        .contains(".")
                        ?
                        originalFilename
                                .substring(originalFilename.lastIndexOf("."))
                        :
                        "";

        String newFilename = metadata.getId() + extension;
        fileService.saveFile(fileByteArray, newFilename);

        return metadata;
    }

    public Metadata updateFileMetadata(Metadata metadata, byte[] fileByteArray, String originalFilename) throws NoSuchElementException, IOException {
        String newExtension =
                originalFilename
                        .contains(".")
                        ?
                        originalFilename
                                .substring(originalFilename.lastIndexOf("."))
                        :
                        "";
        String newFilename = metadata.getId() + newExtension;

        String oldExtension =
                metadata
                        .getOriginalFilename()
                        .contains(".")
                        ?
                        metadata
                                .getOriginalFilename()
                                .substring(metadata.getOriginalFilename().lastIndexOf("."))
                        :
                        "";

        String oldFilename = metadata.getId() + oldExtension;
        fileService.updateFile(oldFilename, fileByteArray, newFilename);

        metadata.setOriginalFilename(originalFilename);
        metadata.setPath(filesPath);

        return metadataService.saveMetadata(metadata);
    }

    public void deleteFile(int id) throws NoSuchElementException, IOException {
        Metadata metadata = metadataService
                .getMetadata(id)
                .orElseThrow();

        String oldExtension =
                metadata
                        .getOriginalFilename()
                        .contains(".")
                ?
                metadata
                        .getOriginalFilename()
                        .substring(metadata.getOriginalFilename().lastIndexOf("."))
                :
                "";

        String oldFilename = metadata.getId() + oldExtension;
        fileService.deleteFile(oldFilename);
    }

    public FileContentDto getFile(int metadataId) throws NoSuchElementException, IOException {
        Metadata metadata = getMetadata(metadataId);

        String extension =
                metadata
                        .getOriginalFilename()
                        .contains(".")
                        ?
                        metadata
                                .getOriginalFilename()
                                .substring(metadata.getOriginalFilename().lastIndexOf("."))
                        :
                        "";

        String fullFilename = metadata.getId() + extension;
        byte[] bytes = fileService.getFile(fullFilename);

        return new FileContentDto(metadata.getOriginalFilename(), bytes);
    }

    public Metadata getMetadata(int metadataId) {
        return metadataService
                .getMetadata(metadataId)
                .orElseThrow();
    }
}
