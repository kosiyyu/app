package com.project.app.api.v1.service;

import com.project.app.api.v1.entity.Tag;
import com.project.app.api.v1.repository.TagRepository;
import com.project.app.exception.AlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TagServiceTest {
    @InjectMocks
    private TagService tagService;
    @Mock
    private TagRepository tagRepository;

    @BeforeEach
    public void setup() {
        tagRepository = Mockito.mock(TagRepository.class);
        tagService = new TagService(tagRepository);
    }

    @Test
    public void testSaveTag() {
        Tag tag = new Tag();
        tag.setValue("TEST");

        tagService.saveTag(tag);

        verify(tagRepository).save(tag);
    }

    @Test
    public void testGetTags() {
        Tag tag1 = new Tag();
        Tag tag2 = new Tag();
        when(tagRepository.findAll()).thenReturn(Arrays.asList(tag1, tag2));

        List<Tag> tags = tagService.getTags();

        assertEquals(2, tags.size());
        verify(tagRepository).findAll();
    }

    @Test
    public void testGetTag() {
        Tag tag = new Tag();
        tag.setId(1);
        when(tagRepository.findById(1)).thenReturn(Optional.of(tag));

        Optional<Tag> result = tagService.getTag(1);

        assertTrue(result.isPresent());
        assertEquals(tag, result.get());
    }

    @Test
    public void testGetFirstTagByValue() {
        Tag tag = new Tag();
        tag.setValue("TEST");
        when(tagRepository.findFirstByValue("TEST")).thenReturn(Optional.of(tag));

        Optional<Tag> result = tagService.getFirstTagByValue("TEST");

        assertTrue(result.isPresent());
        assertEquals(tag, result.get());
    }

    @Test
    public void testEditTag() throws NoSuchElementException, AlreadyExistsException {
        Tag originalTag = new Tag();
        originalTag.setId(1);
        originalTag.setValue("originalValue");

        Tag newTag = new Tag();
        newTag.setId(1);
        newTag.setValue("newValue");

        when(tagRepository.findById(1)).thenReturn(Optional.of(originalTag));
        when(tagRepository.existsByValue("newValue")).thenReturn(false);

        tagService.editTag(newTag);

        assertEquals("newValue", originalTag.getValue());
        verify(tagRepository).save(originalTag);
    }

    @Test
    public void testDeleteTag() throws NoSuchElementException {
        Tag tag = new Tag();
        tag.setId(1);
        when(tagRepository.findById(1)).thenReturn(Optional.of(tag));

        tagService.deleteTag(1);

        verify(tagRepository).deleteById(1);
    }

    @Test
    public void testSafeSaveTag() {
        Tag tag1 = new Tag();
        tag1.setValue("TEST");

        Tag tag2 = new Tag();
        tag2.setValue("TEST");

        tagService.saveTag(tag1);

        when(tagRepository.existsByValue("TEST")).thenReturn(true);

        assertThrows(AlreadyExistsException.class, () -> {
            tagService.safeSaveTag(tag2);
        });
    }
}
