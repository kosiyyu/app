package com.project.app.Alpha.Model;

import lombok.Data;

@Data
public class FileDto {
    String fileName;
    String fileExtension;
    byte[] bytes;
}
