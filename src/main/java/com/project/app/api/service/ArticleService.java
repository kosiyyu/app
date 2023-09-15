package com.project.app.api.service;

import com.project.app.api.entity.Article;
import com.project.app.api.entity.Tag;
import com.project.app.api.repository.ArticleRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    private final TagService tagService;

    public ArticleService(ArticleRepository articleRepository, TagService tagService) {
        this.articleRepository = articleRepository;
        this.tagService = tagService;
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

    @Transactional
    public Article saveArticleWithUniqueTags(Article article) {
        for (int i = 0; i < article.getTags().size(); i++) {
            Tag tag = article.getTags().get(i);
            Optional<Tag> checkTag = tagService.getFirstByValue(tag.getValue());

            if (checkTag.isPresent()) {
                article.getTags().set(i, checkTag.get());
            } else {
                tagService.save(tag);
            }
        }

        return save(article);
    }
}
