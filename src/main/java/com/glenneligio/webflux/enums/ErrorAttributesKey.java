package com.glenneligio.webflux.enums;

import lombok.Getter;

@Getter
public enum ErrorAttributesKey{
    CODE("code"),
    MESSAGE("message"),
    TIME("timestamp"),
    PATH("path");

    private final String key;
    ErrorAttributesKey(String key) {
        this.key = key;
    }
}