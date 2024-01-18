package com.project.app;

import com.project.app.api.v1.entity.Journal;
import com.project.app.api.v1.entity.Metadata;
import com.project.app.api.v1.entity.Tag;
import com.project.app.api.v1.repository.JournalRepository;
import com.project.app.api.v1.service.JournalService;
import com.project.app.api.v1.service.TagService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class JournalServiceTest {

    @Autowired
    private JournalService journalService;

    @Autowired
    private TagService tagService;

    @Autowired
    private JournalRepository journalRepository;

    @AfterEach
    public void reset() {
        journalRepository.deleteAll();
    }

    @Test
    public void testSaveJournalWithUniqueTags() {
        Tag tag = new Tag();
        tag.setValue("Test Tag");
        tagService.saveTag(tag);

        Journal journal = new Journal();
        journal.setTags(Arrays.asList(tag));
        journalService.saveJournalWithUniqueTags(journal);

        Optional<Journal> savedJournal = journalService.getJournal(journal.getId());

        assertTrue(savedJournal.isPresent());
        assertEquals(1, savedJournal.get().getTags().size());
        assertEquals("Test Tag", savedJournal.get().getTags().get(0).getValue());
    }

    @Test
    public void testDeleteJournal() {
        Journal journal = new Journal();
        journalService.saveJournal(journal);

        journalService.deleteJournal(journal.getId());

        Optional<Journal> deletedJournal = journalService.getJournal(journal.getId());
        assertFalse(deletedJournal.isPresent());
    }

    @Test
    public void testEditJournal() {
        Journal journal = new Journal();
        journalService.saveJournal(journal);

        journal.setTitle1("New Title");
        journalService.editJournal(journal);

        Optional<Journal> editedJournal = journalService.getJournal(journal.getId());
        assertTrue(editedJournal.isPresent());
        assertEquals("New Title", editedJournal.get().getTitle1());
    }

    @Test
    public void testGetMetadataId() {
        Metadata metadata = new Metadata();

        Journal journal = new Journal();
        journal.setMetadata(metadata);

        journalService.saveJournal(journal);

        Optional<Integer> metadataId = journalService.getMetadataId(journal.getId());

        assertTrue(metadataId.isPresent());
        assertEquals(1, metadataId.get());
    }

    @Test
    public void testSaveAllJournalsWithUniqueTags() {
        Tag tag = new Tag();
        tag.setValue("Test Tag");

        Tag tag1 = new Tag();
        tag1.setValue("Test Tag1");

        Tag tag2 = new Tag();
        tag2.setValue("Test Tag2");

        Journal journal = new Journal();
        journal.setTags(Arrays.asList(tag, tag1));

        Journal journal1 = new Journal();
        journal1.setTags(Arrays.asList(tag, tag2));

        journalService.saveAllJournalsWithUniqueTags(Arrays.asList(journal, journal1));

        Optional<Journal> savedJournal = journalService.getJournal(journal.getId());
        Optional<Journal> savedJournal1 = journalService.getJournal(journal1.getId());

        assertTrue(savedJournal.isPresent());
        assertTrue(savedJournal1.isPresent());

        assertEquals(2, savedJournal.get().getTags().size());
        assertEquals(2, savedJournal1.get().getTags().size());

        List<Tag> allTags = tagService.getTags();
        assertEquals(3, allTags.size());
    }
}
