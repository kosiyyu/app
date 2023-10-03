package com.project.app.api.service;

import com.project.app.api.entity.Article;
import com.project.app.api.entity.Tag;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

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
        List<Tag> tags = null;
        while ((line = bufferedReader.readLine()) != null) {
            String[] arr = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
            if(index == 0){
                tags = Arrays.stream(Arrays.copyOfRange(arr, 9, arr.length))
                        .map(Tag::new)
                        .toList();
                tags = tagService.saveAll(tags);
            }
            else if(index == 1){
                index++;
                continue;
            }
            else {
                List<String> values = Arrays.stream(Arrays.copyOfRange(arr, 9, arr.length))
                        .toList();
                List<Tag> localTags = new ArrayList<>();
                for(int i = 0; i < values.size(); i++){
                    if(values.get(i).equals("x")){
                        localTags.add(tags.get(i));
                    }
                }
                Article article = new Article(arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], Integer.parseInt(arr[8]), localTags);
                articleService.saveArticleWithUniqueTags(article);
            }
            index++;
        }
    }

    public void loadCsv2(byte[] bytes) throws IOException {

        final BufferedReader bufferedReader = new BufferedReader(new StringReader(new String(bytes, StandardCharsets.UTF_8)));
        int lineIndex = 0;
        String line = null;
        char[] chars;

        String[] values = new String[44];
        Map<Integer, String> map = new HashMap<>();
        while ((line = bufferedReader.readLine()) != null) {
            if(lineIndex == 0){
                lineIndex++;
                int start = -1;
                int end = -1;
                int valueCounter = 0;
                for(int i = line.length() - 1; i >= 0; i--) {
                    if(line.charAt(i) == '"' && i == line.length() - 1){
                        end = i;
                    }
                    else if(line.charAt(i) == '"' && line.charAt(i - 1) == ',' && line.charAt(i - 2) == '"') {
                        start = i + 1;
                        values[valueCounter] = line.substring(start, end);
                        valueCounter++;
                        end = i - 2;
                        i -= 2;
                    }
                    else if(line.charAt(i) == ',' && line.charAt(i - 1) == '"'){
                        // end
                        start = i + 1;
                        values[valueCounter] = line.substring(start, end);
                        valueCounter++;
                        end = i - 1;
                    }
                    else if(line.charAt(i) == ',' && line.charAt(i + 1) == '"'){
                        // start
                        start = i + 2;
                        values[valueCounter] = line.substring(start, end);
                        valueCounter++;
                        end = i;
                    }
                    else if(line.charAt(i) == ','){

                    }
                }
            }

            char []charArray = line.toCharArray();
            Vector<Integer> split = new Vector<>();
            String[] stringArray = new String[5];


            // 0 1 2            3         4         5            67         8
            // 1,1,2D Materials,2053-1583,2053-1583,2D Materials,,2053-1583,140,,,,,,,,x,x,,x,,x,x,x,,x,,,,,,,,,,,,,,,,,,,,,,,,x,x,,
            int wordCounter = 1; // initial is 0 line.indexOf(',') witch always will be a number
            int splitAt = 0;
            for(int i = line.indexOf(','); i < charArray.length; i++) {
                if(charArray[i] == ',') {
                    if(charArray[i + 1] == '"')
                    {
                        split.add(i + 2);
                    }
                    else if(charArray[i - 1] == '"')
                    {
                        split.add(i - 2);
                    }
                    else {
                        split.add(i);
                    }
                }
                if(charArray[i + 1] == '"')
                {

                }
            }
            lineIndex++;
        }
    }
}
