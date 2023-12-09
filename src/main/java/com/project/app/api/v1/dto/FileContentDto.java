package com.project.app.api.v1.dto;

public record FileContentDto(String fullFilename, byte[] bytes) {
}