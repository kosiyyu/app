package com.project.app.api.service;

import com.project.app.api.dto.CustomSearchDto;
import com.project.app.api.dto.SearchTokenDto;
import com.project.app.api.entity.Journal;
import com.project.app.api.entity.Metadata;
import com.project.app.api.entity.Tag;
import com.project.app.api.repository.JournalRepository;
import com.project.app.tools.AlreadyExistsException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class JournalService {
    private final JournalRepository journalRepository;
    private final TagService tagService;

    public JournalService(JournalRepository journalRepository, TagService tagService) {
        this.journalRepository = journalRepository;
        this.tagService = tagService;
    }

    public Journal save(Journal journal) {
        return journalRepository.save(journal);
    }

    public Optional<Journal> get(int id) {
        return journalRepository.findById(id);
    }

    public List<Journal> getAll() {
        return journalRepository.findAll();
    }

    @Transactional
    public Journal saveJournalWithUniqueTags(Journal journal) {
        for (int i = 0; i < journal.getTags().size(); i++) {
            Tag tag = journal.getTags().get(i);
            Optional<Tag> checkTag = tagService.getFirstByValue(tag.getValue());
            if (checkTag.isPresent()) {
                journal.getTags().set(i, checkTag.get());
            } else {
                tagService.save(tag);
            }
        }
        return save(journal);
    }

    public void delete(int id) throws NoSuchElementException {
        journalRepository.findById(id).orElseThrow();
        // delete file
        journalRepository.deleteById(id);

    }

    public void patch(Journal journal) throws NoSuchElementException {
        journalRepository.findById(journal.getId()).orElseThrow();
        journalRepository.save(journal);
    }

    public Optional<Integer> getMetadataId(int journalId) throws NoSuchElementException{
        Journal journal = journalRepository.findById(journalId).orElseThrow();
        return Optional.ofNullable(journal.getMetadata())
                .map(Metadata::getId)
                .filter(x -> x > 0);
    }

    public void saveAll(List<Journal> journals){
        journalRepository.saveAll(journals);
    }

    public List<Journal> saveAllJournalsWithUniqueTags(List<Journal> journals) {
        for (Journal journal : journals) {
            for (int i = 0; i < journal.getTags().size(); i++) {
                Tag tag = journal.getTags().get(i);
                Optional<Tag> checkTag = tagService.getFirstByValue(tag.getValue());
                if (checkTag.isPresent()) {
                    journal.getTags().set(i, checkTag.get());
                } else {
                    tagService.save(tag);
                }
            }
        }
        return journalRepository.saveAll(journals);
    }

}
