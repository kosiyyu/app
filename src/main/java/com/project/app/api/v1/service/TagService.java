package com.project.app.api.v1.service;

import com.project.app.api.v1.entity.Tag;
import com.project.app.api.v1.repository.TagRepository;
import com.project.app.exception.AlreadyExistsException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TagService {
    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public void saveTag(Tag tag) {
        tagRepository.save(tag);
    }

    public List<Tag> getTags() {
        return tagRepository.findAll();
    }

    public Optional<Tag> getTag(int tagId) {
        return tagRepository.findById(tagId);
    }

    private boolean contains(String value) {
        return tagRepository.existsByValue(value);
    }

    public Optional<Tag> getFirstTagByValue(String value) {
        return tagRepository.findFirstByValue(value);
    }

    public void editTag(Tag a) throws NoSuchElementException, AlreadyExistsException {
        if(contains(a.getValue())){
            throw new AlreadyExistsException();
        }
        Tag b = tagRepository.findById(a.getId()).orElseThrow();
        b.setValue(a.getValue());
        tagRepository.save(b);
    }

    public void deleteTag(int tagId) throws NoSuchElementException {
        tagRepository.findById(tagId).orElseThrow();
        tagRepository.deleteById(tagId);
    }

    public void safeSaveTag(Tag tag) throws IllegalArgumentException, AlreadyExistsException {
        if(tag.getId() != 0){
            throw new IllegalArgumentException();
        }
        if(tagRepository.existsByValue(tag.getValue())){
            throw new AlreadyExistsException();
        }
        tagRepository.save(tag);
    }

}
