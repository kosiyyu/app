package com.project.app.api.service;

import com.project.app.api.entity.Metadata;
import com.project.app.api.repository.FileMetadataRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MetadataService {
    private final FileMetadataRepository fileMetadataRepository;

    public MetadataService(FileMetadataRepository fileMetadataRepository) {
        this.fileMetadataRepository = fileMetadataRepository;
    }

    public Metadata save(Metadata metaData) {
        return this.fileMetadataRepository.save(metaData);
    }

    public Optional<Metadata> findById(int id) {
        return fileMetadataRepository.findById(id);
    }
}
