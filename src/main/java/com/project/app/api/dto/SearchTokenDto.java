package com.project.app.api.dto;


import java.util.List;

public record SearchTokenDto(List<String> whereArguments, String orderByArgument, int pageIndex, int pageSize, boolean isDescSort) {
}