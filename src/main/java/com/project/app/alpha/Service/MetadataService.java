package com.project.app.alpha.Service;

import com.project.app.alpha.Model.Metadata;
import com.project.app.alpha.Repository.FileMetadataRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MetadataService {

    private final FileMetadataRepository fileMetadataRepository;

    public MetadataService(FileMetadataRepository fileMetadataRepository){
        this.fileMetadataRepository = fileMetadataRepository;
    }

    public Metadata save(Metadata metaData){
        return this.fileMetadataRepository.save(metaData);
    }

    public List<Metadata> saveList(List<Metadata> metadataList){
        return this.fileMetadataRepository.saveAll(metadataList);
    }

    public List<Metadata> findAll(){
        return fileMetadataRepository.findAll();
    }

    public Metadata find(String fullFilename){
        return fileMetadataRepository.findById(fullFilename).get();
    }
}
