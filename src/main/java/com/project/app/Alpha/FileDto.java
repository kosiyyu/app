package com.project.app.Alpha;

import lombok.Data;

@Data
public class FileDto {
    String fileName;
    String fileExtension;
    byte[] bytes;
}
