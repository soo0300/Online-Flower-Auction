package com.kkoch.admin.domain;

import lombok.Getter;

@Getter
public enum Grade {
    NONE("테스트"),
    Super("특급"),
    Advanced("상급"),
    Normal("보통");


    private final String text;

    Grade(String text) {
        this.text = text;
    }
}
