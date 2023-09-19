package com.project.app.api.service;

import com.project.app.api.entity.Article;
import com.project.app.api.entity.Tag;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@Service
public class CsvService {
    final private TagService tagService;
    final private ArticleService articleService;

    public CsvService(TagService tagService, ArticleService articleService){
        this.tagService = tagService;
        this.articleService = articleService;
    }
    public void loadCsv(byte[] bytes) throws IOException {

        final BufferedReader bufferedReader = new BufferedReader(new StringReader(new String(bytes, StandardCharsets.UTF_8)));
        int index = 0; // csv iteration
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] arr = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
            if(index == 0){
                List<Tag> tags = Arrays.stream(Arrays.copyOfRange(arr, 9, arr.length))
                        .map(Tag::new)
                        .toList();
                tags.forEach(tagService::save);
            }
            else if(index == 1){
                index++;
                continue;
            }
            else {
                List<Tag> localTags = Arrays.stream(Arrays.copyOfRange(arr, 9, arr.length))
                        .filter(x -> x.equals("x"))
                        .map(Tag::new)
                        .toList();
                Article article = new Article(arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], Integer.parseInt(arr[8]), localTags);
                articleService.save(article);
            }
            index++;
        }
    }
}
