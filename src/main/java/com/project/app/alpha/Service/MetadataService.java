package com.project.app.alpha.Service;

import com.project.app.alpha.Model.Metadata;
import com.project.app.alpha.Repository.MetadataRepository;
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
