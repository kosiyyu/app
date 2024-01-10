package com.project.app.api.v1.dto;


import java.util.List;

public record SearchTokenDto
        (
        List<String> searchStrings,
        List<String> tagStrings,
        String orderByArgument,
        int pageIndex,
        int pageSize,
        boolean isDescSort,
        boolean isOr,
        String similarityString,
        double similarityValue
        ) { }