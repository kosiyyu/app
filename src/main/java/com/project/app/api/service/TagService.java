package com.project.app.api.service;

import com.project.app.api.entity.Tag;
import com.project.app.api.repository.TagRepository;
import com.project.app.tools.AlreadyExistsException;
import com.sun.nio.sctp.IllegalUnbindException;
import org.springframework.dao.DataIntegrityViolationException;
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

    public Optional<Tag> getFirstByValue(String value) {
        return tagRepository.findFirstByValue(value);
    }

    public void patch(Tag a) throws NoSuchElementException, AlreadyExistsException {
        if(contains(a.getValue())){
            throw new AlreadyExistsException();
        }
        Tag b = tagRepository.findById(a.getId()).orElseThrow();
        b.setValue(a.getValue());
        tagRepository.save(b);
    }

    public void delete(int id) throws NoSuchElementException {
        tagRepository.findById(id).orElseThrow();
        tagRepository.deleteById(id);
    }

    public void saveSafe(Tag tag) throws IllegalArgumentException, AlreadyExistsException {
        if(tag.getId() != 0){
            throw new IllegalArgumentException();
        }
        if(tagRepository.existsByValue(tag.getValue())){
            throw new AlreadyExistsException();
        }
        tagRepository.save(tag);
    }

}
