package com.project.app.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.app.api.dto.MetadataFileDto;
import com.project.app.api.entity.Metadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.List;

@Service
public class FileService {

    @Value("${FILE_PATH}")
    private String filePath;

    public void save(MultipartFile multipartFile, String newFilename) throws IOException {
        byte[] bytes = multipartFile.getBytes();
        String originalFilename = multipartFile.getOriginalFilename();
        String originalFileExtension = originalFilename.contains(".") ? originalFilename.substring(originalFilename.lastIndexOf(".")) : "";

        String path = filePath + newFilename + originalFileExtension;

        OutputStream file = new FileOutputStream(path);
        file.write(bytes);
        file.flush();
        file.close();
    }

    public void save(byte[] byteArray, String newFilename) throws IOException {

        String path = filePath + newFilename;

        OutputStream file = new FileOutputStream(path);
        file.write(byteArray);
        file.flush();
        file.close();
    }

    public void saveList(List<MultipartFile> multipartFileList, List<String> stringList) throws IOException {
        for (int i = 0; i < multipartFileList.size(); i++) {
            save(multipartFileList.get(i), stringList.get(i));
        }
    }
}
