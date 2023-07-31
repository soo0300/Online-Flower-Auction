package com.kkoch.user.api.service.reservation.dto;

import com.kkoch.user.domain.reservation.Reservation;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddReservationDto {

    private Long memberId;
    private Long plantId;
    private int count;
    private int price;

    @Builder
    public AddReservationDto(Long memberId, Long plantId, int count, int price) {
        this.memberId = memberId;
        this.plantId = plantId;
        this.count = count;
        this.price = price;
    }

    public Reservation toEntity() {
        Reservation reservation = Reservation.builder()
                .memberId(this.memberId)
                .plantId(this.plantId)
                .count(this.count)
                .price(this.price)
                .build();
        return reservation;
    }
}

