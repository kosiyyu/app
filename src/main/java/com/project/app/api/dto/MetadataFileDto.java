package com.project.app.api.dto;

import com.project.app.api.entity.Tag;

import java.util.List;

public record MetadataFileDto(int id, String filename, List<Tag> tags, String encodedByteArray) {
}