package com.project.app.api.model;

import com.project.app.api.entity.Tag;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record MultipartFileTagRecord(MultipartFile multipartFile, List<Tag> tags) {
}
