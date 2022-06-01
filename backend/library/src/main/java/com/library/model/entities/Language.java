package com.library.model.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

public enum Language {
    HU, EN;

    @JsonIgnore
    public boolean isHungarian() {
        return HU.equals(this);
    }
}
