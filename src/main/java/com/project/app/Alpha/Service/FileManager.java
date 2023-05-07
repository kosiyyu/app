package com.project.app.Alpha.Service;

import com.project.app.Alpha.Model.FileDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Service
public class FileManager {

    public boolean saveFile(FileDto fileDto) throws IOException {
        String path = "src/main/resources/files/" + fileDto.getFileName() + "." + fileDto.getFileExtension();
        OutputStream file = new FileOutputStream(path);
        file.write(fileDto.getBytes());
        file.flush();
        file.close();
        return true;
    }

    public boolean saveLargeFile(MultipartFile mpFile, String originalFileName) throws IOException {
        String path = "src/main/resources/files/" + originalFileName;
        OutputStream file = new FileOutputStream(path);
        file.write(mpFile.getBytes());
        file.flush();
        file.close();
        return true;
    }
}
