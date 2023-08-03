package com.project.app.api.service;

import com.project.app.api.model.Tag;
import com.project.app.api.repository.TagRepository;
import org.springframework.stereotype.Service;

@Service
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository){
        this.tagRepository = tagRepository;
    }
    public Tag save(Tag tag){
        return tagRepository.save(tag);
    }
}
