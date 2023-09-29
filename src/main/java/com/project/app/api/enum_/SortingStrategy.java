package com.project.app.api.enum_;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum SortingStrategy {
    @JsonProperty("asc")
    asc,
    @JsonProperty("desc")
    desc
}