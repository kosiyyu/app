package com.project.app.api.enum_;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Comparison {
    @JsonProperty("equal")
    equal,
    @JsonProperty("like")
    like
}