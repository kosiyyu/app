package com.project.app.api.dto;

import com.project.app.api.entity.Metadata;

public record MetadataByteArrayDto(Metadata metadata, byte[] byteArray) {
}
