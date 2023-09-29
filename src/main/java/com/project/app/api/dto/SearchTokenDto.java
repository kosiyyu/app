package com.project.app.api.dto;


public record SearchTokenDto(String searchText, String whereCondition, String orderByCondition, int pageIndex, int pageSize, boolean isDescSort) {
}