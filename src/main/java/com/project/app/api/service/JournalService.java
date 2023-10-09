package com.project.app.api.service;

import com.project.app.api.dto.CustomSearchDto;
import com.project.app.api.dto.SearchTokenDto;
import com.project.app.api.entity.Journal;
import com.project.app.api.entity.Tag;
import com.project.app.api.repository.JournalRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
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

    public CustomSearchDto customSearch(SearchTokenDto searchTokenDto) {
        final Pageable pageable = PageRequest.of(searchTokenDto.pageIndex(), searchTokenDto.pageSize());
        final long numberOfPages = (long)Math.ceil((double) count() / searchTokenDto.pageSize());
        if(searchTokenDto.pageIndex() > numberOfPages || searchTokenDto.pageIndex() < 0) {
            return new CustomSearchDto(numberOfPages, searchTokenDto.pageIndex(), Collections.emptyList());
        }
        return new CustomSearchDto(numberOfPages, searchTokenDto.pageIndex(),
                searchTokenDto.isDescSort()
                    ? journalRepository.customSearchDesc(searchTokenDto.searchText(),searchTokenDto.whereCondition(),searchTokenDto.orderByCondition(), pageable)
                    : journalRepository.customSearchAsc(searchTokenDto.searchText(),searchTokenDto.whereCondition(),searchTokenDto.orderByCondition(), pageable)
                );
    }

    public long count() {
        return journalRepository.count();
    }
}
