package com.kkoch.user.api.service.reservation.dto;

import com.kkoch.user.domain.reservation.Reservation;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EditReservationDto {

    private int count;
    private int price;

    @Builder
    public EditReservationDto(int count, int price) {
        this.count = count;
        this.price = price;
    }

    public Reservation toEntity() {
        Reservation reservation = Reservation.builder()
                .count(count)
                .price(price)
                .build();
        return reservation;
    }
}
