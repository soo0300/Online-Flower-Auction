package com.kkoch.admin.domain;

import lombok.Getter;

@Getter
public enum Grade {
    NONE("테스트");

    private final String text;

    Grade(String text) {
        this.text = text;
    }
}
