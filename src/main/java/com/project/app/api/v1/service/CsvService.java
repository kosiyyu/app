package com.project.app.api.v1.service;

import com.project.app.api.v1.entity.Journal;
import com.project.app.api.v1.entity.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class CsvService {
    @Value("${BATCH_SIZE}")
    private int BATCH_SIZE;
    final private JournalService journalService;

    public CsvService(JournalService journalService){
        this.journalService = journalService;
    }

    public void loadCsv(byte[] bytes) throws IOException {
        final int TAG_START_INDEX = 9;
        Map<Integer, Tag> tagMap = new HashMap<>();
        List<Journal> journals = new LinkedList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new StringReader(new String(bytes, StandardCharsets.UTF_8)))) {
            String line;
            int index = 0;
            while ((line = bufferedReader.readLine()) != null) {
                String[] words = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");

                if (index == 0) {
                    IntStream.rangeClosed(TAG_START_INDEX, words.length - 1)
                            .forEachOrdered(i -> tagMap.put(words.length - 1 - i, new Tag(words[i].replaceAll("^\"|\"$", ""))));

                } else if (index != 1) {
                    List<Tag> tags = IntStream.range(TAG_START_INDEX, words.length)
                            .filter(i -> "x".equals(words[i]))
                            .mapToObj(i -> tagMap.get(words.length - 1 - i))
                            .collect(Collectors.toList());

                    String[] fields = IntStream.range(2, 9)
                            .mapToObj(i -> words[i].replaceAll("^\"|\"$", ""))
                            .toArray(String[]::new);

                    Journal journal = new Journal(
                            fields[0],
                            fields[1],
                            fields[2],
                            fields[3],
                            fields[4],
                            fields[5],
                            fields[6].isBlank() ? 0 : Integer.parseInt(fields[6]),
                            tags);
                    journals.add(journal);

                    if (journals.size() == BATCH_SIZE) {
                        journalService.saveAllJournalsWithUniqueTags(journals);
                        journals.clear();
                    }
                }
                index++;
            }
        }
        if (!journals.isEmpty()) {
            journalService.saveAllJournalsWithUniqueTags(journals);
        }
    }
}
