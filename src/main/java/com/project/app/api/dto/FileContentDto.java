package com.project.app.api.dto;

public record FileContentDto(String fullFilename, byte[] bytes) {
}