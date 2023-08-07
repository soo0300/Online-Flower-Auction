package com.kkoch.admin.api.controller.stats.response;

import com.kkoch.admin.domain.Grade;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@Data
public class StatsResponse {

    private int priceAvg;

    private int priceMax;

    private int priceMin;

    private Grade grade;

    private int count;

    private String createdDate;

    private String name;

    private String type;

    @Builder
    public StatsResponse(int priceAvg, int priceMax, int priceMin, Grade grade, int count, LocalDateTime createdDate, String name, String type) {
        this.priceAvg = priceAvg;
        this.priceMax = priceMax;
        this.priceMin = priceMin;
        this.grade = grade;
        this.count = count;
        this.createdDate = createdDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd hh:mm"));
        this.name = name;
        this.type = type;
    }
}
