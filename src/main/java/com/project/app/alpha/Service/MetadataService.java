package com.project.app.alpha.Service;

import com.project.app.alpha.Model.FileMetadata;
import com.project.app.alpha.Repository.FileMetadataRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MetadataService {

    private final FileMetadataRepository fileMetadataRepository;

    public MetadataService(FileMetadataRepository fileMetadataRepository){
        this.fileMetadataRepository = fileMetadataRepository;
    }

    public FileMetadata add(FileMetadata metaData){
        return this.fileMetadataRepository.save(metaData);
    }

    public List<FileMetadata> findAll(){
        return fileMetadataRepository.findAll();
    }

    public FileMetadata get(String fullFilename){
        return fileMetadataRepository.findById(fullFilename).get();
    }
}
