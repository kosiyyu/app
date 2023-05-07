package com.project.app.Alpha.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Service
public class FileService {

    @Value("${FILES_PATH}")
    private String filesPath;

    public boolean saveLargeFile(MultipartFile mpFile, String originalFileName) throws IOException {
        String path = filesPath + originalFileName;
        OutputStream file = new FileOutputStream(path);
        file.write(mpFile.getBytes());
        file.flush();
        file.close();
        return true;
    }
}
