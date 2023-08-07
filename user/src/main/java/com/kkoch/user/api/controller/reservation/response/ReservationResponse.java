package com.kkoch.user.api.controller.reservation.response;

import com.kkoch.user.domain.Grade;
import com.kkoch.user.domain.reservation.Reservation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
public class ReservationResponse {

    private int count;
    private int price;
    private String grade;
    private String createdDate;

    @Builder
    private ReservationResponse(int count, int price, String grade, LocalDateTime createdDate) {
        this.count = count;
        this.price = price;
        this.grade = grade;
        this.createdDate = createdDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd hh:mm"));
    }

    public static ReservationResponse of(Reservation reservation) {
        return ReservationResponse.builder()
            .count(reservation.getCount())
            .price(reservation.getPrice())
            .grade(reservation.getGrade().getText())
            .createdDate(reservation.getCreatedDate())
            .build();
    }
}
