package com.project.app.alpha.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Service
public class FileService {

    @Value("${FILES_PATH}")
    private String filesPath;

    public void save(MultipartFile multipartFile) throws IOException {
        byte[] bytes = multipartFile.getBytes();
        String originalFileName = multipartFile.getOriginalFilename();

        String path = filesPath + originalFileName;
        OutputStream file = new FileOutputStream(path);
        file.write(bytes);
        file.flush();
        file.close();
    }

    public void saveList(List<MultipartFile> multipartFileList) throws IOException {
        for(var m : multipartFileList){
            save(m);
        }
    }
}
