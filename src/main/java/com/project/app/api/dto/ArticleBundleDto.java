package com.project.app.api.dto;

import com.project.app.api.entity.Article;

public record ArticleBundleDto(Article article, String originalFilename, byte[] byteArray) {
}
