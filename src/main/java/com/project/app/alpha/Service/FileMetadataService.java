package com.project.app.alpha.Service;

import com.project.app.alpha.Model.Metadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileMetadataService {

    @Value("${FILES_PATH}")
    private String filesPath;

    private final MetadataService metadataService;

    private final FileService fileService;

    public FileMetadataService(MetadataService metadataService, FileService fileService){
        this.metadataService = metadataService;
        this.fileService = fileService;
    }

    public String save(MultipartFile multipartFile) throws IOException {
        String originalFileName = multipartFile.getOriginalFilename();
        String extentionWithDot = originalFileName.substring(originalFileName.lastIndexOf('.') == -1 ? 0 : originalFileName.lastIndexOf('.'));

        //save in db
        Metadata metadata = new Metadata(originalFileName, filesPath);
        metadata = metadataService.add(metadata);

        //save in dir
        fileService.saveLargeFile(multipartFile.getBytes(), metadata.getId() + extentionWithDot);

        //not needed, but I will keep it for now ;))
        return multipartFile.getBytes().toString();
    }



}
