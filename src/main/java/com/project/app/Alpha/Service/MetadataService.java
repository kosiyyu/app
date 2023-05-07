package com.project.app.Alpha.Service;

import com.project.app.Alpha.Model.Metadata;
import com.project.app.Alpha.Repository.MetadataRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MetadataService {

    private final MetadataRepository metadataRepository;

    public MetadataService(MetadataRepository metadataRepository){
        this.metadataRepository = metadataRepository;
    }

    public Metadata add(Metadata metaData){
        return this.metadataRepository.save(metaData);
    }

    public List<Metadata> findAll(){
        return metadataRepository.findAll();
    }

    public Metadata get(String fullFilename){
        return metadataRepository.findById(fullFilename).get();
    }
}
