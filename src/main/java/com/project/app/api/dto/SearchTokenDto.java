package com.project.app.api.dto;

import com.project.app.api.enum_.Comparison;
import com.project.app.api.enum_.Operator;
import com.project.app.api.enum_.SortingStrategy;

public record SearchTokenDto(String match, Comparison comparison, Integer number, Operator operator, String sortingField, SortingStrategy sortingStrategy, Integer pageNumber) {
}