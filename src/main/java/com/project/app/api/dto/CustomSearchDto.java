package com.project.app.api.dto;

import com.project.app.api.entity.Journal;

import java.util.List;

public record CustomSearchDto(long numberOfPages, long pageNumber, List<Journal> journals) {
}
