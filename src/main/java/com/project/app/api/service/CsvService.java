package com.project.app.api.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvService {
    public void getDataFromCsv(byte[] bytes) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(new ByteArrayInputStream(bytes));
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        List<String[]> valuesList = new ArrayList<>();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] values = line.split(",");
            valuesList.add(values);
        }
        //todo valuesList
    }
}
