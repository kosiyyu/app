package com.project.app;

import com.project.app.api.v1.entity.Tag;
import com.project.app.api.v1.repository.TagRepository;
import com.project.app.api.v1.service.TagService;
import com.project.app.exception.AlreadyExistsException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class TagServiceTest {

    @Autowired
    private TagService tagService;

    @Autowired
    private TagRepository tagRepository;

    @AfterEach
    public void reset() {
        tagRepository.deleteAll();
    }

    @Test
    public void testSaveTag() {
        Tag tag = new Tag();
        tag.setValue("Test Tag");
        tagService.saveTag(tag);

        Optional<Tag> savedTag = tagService.getFirstTagByValue("Test Tag");
        assertTrue(savedTag.isPresent());
        assertEquals("Test Tag", savedTag.get().getValue());
    }

    @Test
    public void testEditTag() {
        try {
            Tag tag = new Tag();
            tag.setValue("Test Tag");
            tagService.saveTag(tag);

            Tag savedTag = tagService.getFirstTagByValue("Test Tag").orElseThrow();
            savedTag.setValue("Updated Tag");
            tagService.editTag(savedTag);

            Optional<Tag> updatedTag = tagService.getFirstTagByValue("Updated Tag");
            assertTrue(updatedTag.isPresent());
            assertEquals("Updated Tag", updatedTag.get().getValue());
        } catch (Exception e) {
            fail("Exception should not be thrown: " + e.getMessage());
        }
    }

    @Test
    public void testSafeSaveTag() {
        Tag tag = new Tag();
        tag.setValue("Test Tag");

        Tag tag1 = new Tag();
        tag1.setValue("Test Tag");

        assertThrows(AlreadyExistsException.class, () -> {
            tagService.safeSaveTag(tag);
            tagService.safeSaveTag(tag1);
        });
    }

    @Test
    public void testGetTags() {
        Tag tag1 = new Tag();
        tag1.setValue("Test Tag 1");
        tagService.saveTag(tag1);

        Tag tag2 = new Tag();
        tag2.setValue("Test Tag 2");
        tagService.saveTag(tag2);

        List<Tag> tags = tagService.getTags();
        assertEquals(2, tags.size());
        assertTrue(tags.stream().anyMatch(tag -> tag.getValue().equals("Test Tag 1")));
        assertTrue(tags.stream().anyMatch(tag -> tag.getValue().equals("Test Tag 2")));
    }

    @Test
    public void testGetFirstTagByValue() {
       Tag tag = new Tag();
       tag.setValue("Test Tag");
       tagService.saveTag(tag);

       Optional<Tag> retrievedTag = tagService.getFirstTagByValue("Test Tag");
       assertTrue(retrievedTag.isPresent());
       assertEquals("Test Tag", retrievedTag.get().getValue());

       Optional<Tag> nonexistentTag = tagService.getFirstTagByValue("Nonexistent Tag");
       assertFalse(nonexistentTag.isPresent());
        // assertEquals(1, 1);
    }

    @Test
    public void testGetTag() {
       Tag tag = new Tag();
       tag.setValue("Test Tag");
       tagService.saveTag(tag);

       Tag savedTag = tagService.getFirstTagByValue("Test Tag").orElseThrow();
       Optional<Tag> retrievedTag = tagService.getTag(savedTag.getId());

       assertTrue(retrievedTag.isPresent());
       assertEquals("Test Tag", retrievedTag.get().getValue());
        // assertEquals(1, 1);
    }

    @Test
    public void testDeleteTag() {
        Tag tag = new Tag();
        tag.setValue("Test Tag");
        tagService.saveTag(tag);

        Tag savedTag = tagService.getFirstTagByValue("Test Tag").orElseThrow();
        tagService.deleteTag(savedTag.getId());

        Optional<Tag> deletedTag = tagService.getFirstTagByValue("Test Tag");
        assertFalse(deletedTag.isPresent());
    }

}