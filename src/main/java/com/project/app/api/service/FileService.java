package com.project.app.api.service;

import com.project.app.api.entity.Metadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
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

    public void delete(String oldFilename) throws IOException {
        String path = filePath + oldFilename;
        File file = new File(path);
        if(file.isFile()) {
            if (!file.delete()) {
                throw new IOException("Failed to delete a file.");
            }
        }
    }

    public void update(String oldFilename, byte[] newByteArray, String newFilename) throws IOException {
        String path = filePath + oldFilename;
        File oldFile = new File(path);
        if(oldFile.isFile()){
            if(!oldFile.delete()) {
                throw new IOException();
            }
        } else {
            throw new IOException();
        }
        File newFile = new File(filePath + newFilename);
        try (OutputStream outputStream = new FileOutputStream(newFile)) {
            outputStream.write(newByteArray);
        }
    }

}
