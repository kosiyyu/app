package com.project.app.api.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Service
public class FileService {
    @Value("${FILE_PATH}")
    private String filePath;

    public void save(byte[] byteArray, String newFilename) throws IOException {
        String path = filePath + newFilename;
        OutputStream file = new FileOutputStream(path);
        file.write(byteArray);
        file.flush();
        file.close();
    }
}
