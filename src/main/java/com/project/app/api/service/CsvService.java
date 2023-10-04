package com.project.app.api.service;

import com.project.app.api.entity.Article;
import com.project.app.api.entity.Tag;
import org.apache.el.stream.Stream;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.IntStream;

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

    private static void extractValues(String line, String[] values, int numberOfWords) {
        int start = -1;
        int end = line.length();
        int valueCounter = 0;
        boolean isOpen = false;
        for (int i = line.length() - 1; i >= 0; i--) {
            if (line.charAt(i) == '"' && i == line.length() - 1) {
                end = i;
            } else if (line.charAt(i) == '"' && line.charAt(i - 1) == ',' && line.charAt(i - 2) == '"') {
                values[valueCounter] = line.substring(i + 1, end);
                valueCounter++;
                end = i - 2;
                isOpen = true;
                i -= 2;
            } else if (line.charAt(i) == ',' && line.charAt(i - 1) == '"') {
                values[valueCounter] = line.substring(i + 1, end);
                isOpen = true;
                valueCounter++;
                end = i - 1;
            } else if (line.charAt(i) == ',' && line.charAt(i + 1) == '"') {
                values[valueCounter] = line.substring(i + 2, end);
                isOpen = false;
                valueCounter++;
                end = i;
            } else if (line.charAt(i) == ',' && line.charAt(i - 1) != '"' && line.charAt(i + 1) != '"' && !isOpen) {
                values[valueCounter] = line.substring(i + 1, end);
                valueCounter++;
                end = i;
            } else if (i == 0) {
                start = 0;
                values[valueCounter] = line.substring(start, end);
                valueCounter++;
            }
            if (valueCounter == numberOfWords) {
                break;
            }
        }
    }

    private static void extractKeys(String line, int[] keys, int numberOfWords) {
        int start = -1;
        int end = line.length();
        int valueCounter = 0;
        boolean isOpen = false;
        try {
            for (int i = line.length() - 1; i >= 0; i--) {
                if (line.charAt(i) == '"' && i == line.length() - 1) {
                    end = i;
                } else if (line.charAt(i) == '"' && line.charAt(i - 1) == ',' && line.charAt(i - 2) == '"') {
                    keys[valueCounter] = Integer.parseInt(line.substring(i + 1, end));
                    valueCounter++;
                    end = i - 2;
                    isOpen = true;
                    i -= 2;
                } else if (line.charAt(i) == ',' && line.charAt(i - 1) == '"') {
                    keys[valueCounter] = Integer.parseInt(line.substring(i + 1, end));
                    isOpen = true;
                    valueCounter++;
                    end = i - 1;
                } else if (line.charAt(i) == ',' && line.charAt(i + 1) == '"') {
                    keys[valueCounter] = Integer.parseInt(line.substring(i + 2, end));
                    isOpen = false;
                    valueCounter++;
                    end = i;
                } else if (line.charAt(i) == ',' && line.charAt(i - 1) != '"' && line.charAt(i + 1) != '"' && !isOpen) {
                    keys[valueCounter] = Integer.parseInt(line.substring(i + 1, end));
                    valueCounter++;
                    end = i;
                } else if (i == 0) {
                    start = 0;
                    keys[valueCounter] = Integer.parseInt(line.substring(start, end));
                    valueCounter++;
                }
                if (valueCounter == numberOfWords) {
                    break;
                }
            }
        }
        catch (Exception e){
            throw new RuntimeException("err: " + e.getMessage() + " ," + valueCounter);
        }
    }

    public void loadCsv2(byte[] bytes) throws IOException {
        final BufferedReader bufferedReader = new BufferedReader(new StringReader(new String(bytes, StandardCharsets.UTF_8)));
        int lineIndex = 0;
        String line;
        int numberOfWords = 44;
        int[] keys = new int[numberOfWords];
        String[] values = new String[numberOfWords];
        Map<Integer, String> map = new HashMap<>();
        while ((line = bufferedReader.readLine()) != null) {
            // todo loops refactor: still
            if (lineIndex == 0) {
                extractValues(line, values, numberOfWords);
            }
            else if(lineIndex == 1) {
                extractKeys(line, keys, numberOfWords);
                IntStream.range(0,numberOfWords - 1).forEach(x -> map.put(keys[x], values[x]));
            }
            else {
            }
            lineIndex++;
        }
    }
}
