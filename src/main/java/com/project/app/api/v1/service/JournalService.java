package com.project.app.api.v1.service;

import com.project.app.api.v1.entity.Journal;
import com.project.app.api.v1.entity.Metadata;
import com.project.app.api.v1.entity.Tag;
import com.project.app.api.v1.repository.JournalRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

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

    public Journal saveJournal(Journal journal) {
        return journalRepository.save(journal);
    }

    public Optional<Journal> getJournal(int journalId) {
        return journalRepository.findById(journalId);
    }

    @Transactional
    public void saveJournalWithUniqueTags(Journal journal) {
        for (int i = 0; i < journal.getTags().size(); i++) {
            Tag tag = journal.getTags().get(i);
            Optional<Tag> checkTag = tagService.getFirstTagByValue(tag.getValue());
            if (checkTag.isPresent()) {
                journal.getTags().set(i, checkTag.get());
            } else {
                tagService.saveTag(tag);
            }
        }
        saveJournal(journal);
    }

    public void deleteJournal(int journalId) throws NoSuchElementException {
        journalRepository.findById(journalId).orElseThrow();
        journalRepository.deleteById(journalId);
    }

    public void editJournal(Journal journal) throws NoSuchElementException {
        journalRepository.findById(journal.getId()).orElseThrow();
        journalRepository.save(journal);
    }

    public Optional<Integer> getMetadataId(int journalId) throws NoSuchElementException{
        Journal journal = journalRepository.findById(journalId).orElseThrow();
        return Optional.ofNullable(journal.getMetadata())
                .map(Metadata::getId)
                .filter(x -> x > 0);
    }

    public void saveAllJournalsWithUniqueTags(List<Journal> journals) {
        for (Journal journal : journals) {
            for (int i = 0; i < journal.getTags().size(); i++) {
                Tag tag = journal.getTags().get(i);
                Optional<Tag> checkTag = tagService.getFirstTagByValue(tag.getValue());
                if (checkTag.isPresent()) {
                    journal.getTags().set(i, checkTag.get());
                } else {
                    tagService.saveTag(tag);
                }
            }
        }
        journalRepository.saveAll(journals);
    }
}
