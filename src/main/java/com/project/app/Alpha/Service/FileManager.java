package com.project.app.Alpha.Service;

import com.project.app.Alpha.Model.FileDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class FileManager {

    public boolean saveFile(FileDto fileDto) throws IOException {
        String path = "src/main/resources/files/" + fileDto.getFileName() + "." + fileDto.getFileExtension();
        OutputStream file = new FileOutputStream(path);
        file.write(fileDto.getBytes());
        file.flush();
        file.close();
        return true;
    }

    //much faster, because we do not load file into memory but save it(transfer) directly to disk
    public boolean saveLargeFile(MultipartFile mpFile) throws IOException {
        String path = "src/main/resources/files/" + mpFile.getOriginalFilename();
        mpFile.transferTo(new File(path));
        return true;
    }
}
