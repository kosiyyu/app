package com.project.app.api.controller;

import com.project.app.api.dto.TokenSearchDto;
import com.project.app.api.entity.Article;
import com.project.app.api.service.SearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${API_V1}")
public class SearchController {
    private final SearchService searchService;

    public SearchController(SearchService searchService){
        this.searchService = searchService;
    }

    @GetMapping("/search/download")
    public ResponseEntity<?> test2(@RequestBody TokenSearchDto tokenSearchDto){
        List<Article> articles = searchService.search(tokenSearchDto);
        return ResponseEntity.status(200).body(articles);
    }
}