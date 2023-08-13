package com.project.app.api.service;

import com.project.app.api.entity.Metadata;
import com.project.app.api.model.MultipartFileTagRecord;
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

    public void saveRecord(MultipartFileTagRecord multipartFileTagRecord) throws IOException {

        // SAVE METADATA TO DATABASE
        Metadata metadata = new Metadata(
                multipartFileTagRecord.multipartFile().getOriginalFilename(),
                filesPath,
                multipartFileTagRecord.tags()
                );
        metadataService.save(metadata);

        // SAVE FILE TO DIRECTORY
        fileService.save(multipartFileTagRecord.multipartFile());
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

    public void saveRecordList(List<MultipartFileTagRecord> multipartFileTagRecordList) throws IOException {

        // SAVE METADATA LIST TO DATABASE
        List<Metadata> metadataList = multipartFileTagRecordList.stream()
                .map(x -> new Metadata(x.multipartFile().getOriginalFilename(), filesPath, x.tags()))
                .toList();
        metadataService.saveList(metadataList);

        // SAVE FILE LIST TO DIRECTORY
        fileService.saveList(multipartFileTagRecordList.stream()
                .map(x -> x.multipartFile())
                .toList());
    }
}
