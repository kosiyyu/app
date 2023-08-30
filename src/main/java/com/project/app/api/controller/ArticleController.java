package com.project.app.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.app.api.entity.Article;
import com.project.app.api.service.ArticleService;
import com.project.app.api.service.FileMetadataService;
import com.project.app.api.service.MetadataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("${API_V1}")
public class ArticleController {

    private final ArticleService articleService;
    private final FileMetadataService fileMetadataService;

    private final MetadataService metadataService;


    public ArticleController(ArticleService articleService, FileMetadataService fileMetadataService, MetadataService metadataService) {
        this.articleService = articleService;
        this.fileMetadataService = fileMetadataService;
        this.metadataService = metadataService;
    }

    @PostMapping("/article/upload")
    public ResponseEntity<?> postArticle(@RequestParam("file") MultipartFile multipartFile, @RequestParam("jsonObject") String json) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        Article article = objectMapper.readValue(json, Article.class);
        if (multipartFile == null) {
            // save file & metadata
            // todo
        }

        try {
            articleService.saveArticleWithUniqueTags(article);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something goes wrong." + e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.OK).body("Article created successfully.");
    }

    @GetMapping("/article/download/{id}")
    public ResponseEntity<?> getArticle(@PathVariable String id) {
        Article article;

        try {
            article = articleService.get(Integer.parseInt(id)).orElseThrow();
        } catch (NumberFormatException numberFormatException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Provided id must be a number.");
        } catch (NoSuchElementException noSuchElementException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Article does not exists.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something goes wrong.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(article);
    }

    @GetMapping("/articles/download")
    public ResponseEntity<?> getArticles() {
        List<Article> articles = articleService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(articles);
    }


}
