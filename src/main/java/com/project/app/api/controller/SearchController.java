package com.project.app.api.controller;

import com.project.app.api.dto.SearchDto;
import com.project.app.api.ennum.Comparison;
import com.project.app.api.ennum.SortingStrategy;
import com.project.app.api.entity.Article;
import com.project.app.api.service.SearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${API_V1}")
public class SearchController {
    private final SearchService searchService;

    public SearchController(SearchService searchService){
        this.searchService = searchService;
    }

    @GetMapping("/test")
    public ResponseEntity<?> test(){
        List<Article> articles = searchService.search(new SearchDto("TEST", Comparison.like, null, null, SortingStrategy.asc));
        return ResponseEntity.status(200).body(articles);
    }
}