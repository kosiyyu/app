package com.project.app.api.dto;

import com.project.app.api.entity.Metadata;
import org.springframework.data.jpa.repository.Meta;

public record MultipartDto(String fullFilename, byte[] bytes) {
}