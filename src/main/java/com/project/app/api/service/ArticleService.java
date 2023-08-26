package com.project.app.api.service;

import com.project.app.api.entity.Article;
import com.project.app.api.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Article save(Article article) {
        return articleRepository.save(article);
    }

    public Optional<Article> get(int id) {
        return articleRepository.findById(id);
    }

    public List<Article> getAll() {
        return articleRepository.findAll();
    }
}
