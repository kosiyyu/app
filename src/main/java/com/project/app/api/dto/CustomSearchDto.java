package com.project.app.api.dto;

import com.project.app.api.entity.Article;

import java.util.List;

public record CustomSearchDto(long numberOfPages, long pageNumber, List<Article> articles) {
}
