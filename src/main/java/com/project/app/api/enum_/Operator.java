package com.project.app.api.enum_;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Operator {
    @JsonProperty("equal")
    equal,
    @JsonProperty("graterThan")
    graterThan,
    @JsonProperty("graterOrEqualTo")
    graterOrEqualTo,
    @JsonProperty("lesserThan")
    lesserThan,
    @JsonProperty("lesserThanOrEqualTo")
    lesserThanOrEqualTo
}