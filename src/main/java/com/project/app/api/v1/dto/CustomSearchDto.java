package com.project.app.api.v1.dto;

import com.project.app.api.v1.entity.Journal;

import java.util.List;

public record CustomSearchDto(long numberOfPages, long pageNumber, List<Journal> journals) {
}
