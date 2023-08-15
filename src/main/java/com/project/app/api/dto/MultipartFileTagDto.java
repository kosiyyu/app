package com.project.app.api.dto;

import com.project.app.api.entity.Tag;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record MultipartFileTagDto(MultipartFile multipartFile, List<Tag> tags) {
}
