package com.project.app.api.service;

import com.project.app.api.entity.Tag;
import com.project.app.api.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {
    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Tag save(Tag tag) {
        return tagRepository.save(tag);
    }

    public List<Tag> getAll() {
        return tagRepository.findAll();
    }

    public Optional<Tag> get(int id) {
        return tagRepository.findById(id);
    }

    public boolean contains(String value) {
        return tagRepository.existsByValue(value);
    }

    public Optional<Tag> getByValue(String value) {
        return tagRepository.findByValue(value);
    }

    public Optional<Tag> getFirstByValue(String value) {
        return tagRepository.findFirstByValue(value);
    }
}
