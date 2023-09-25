package com.project.app.api.dto;

import com.project.app.api.ennum.Comparison;
import com.project.app.api.ennum.Operator;
import com.project.app.api.ennum.SortingStrategy;

public record SearchDto(String match, Comparison comparison, Integer number, Operator operator, SortingStrategy sortingStrategy, int pageNumber) {
}