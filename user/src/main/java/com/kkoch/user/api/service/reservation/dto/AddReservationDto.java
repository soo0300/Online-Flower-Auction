package com.kkoch.user.api.service.reservation.dto;

import com.kkoch.user.domain.member.Member;
import com.kkoch.user.domain.reservation.Reservation;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddReservationDto {

    private Long plantId;
    private int count;
    private int price;

    @Builder
    public AddReservationDto(Long plantId, int count, int price) {
        this.plantId = plantId;
        this.count = count;
        this.price = price;
    }

    public Reservation toEntity(Member member) {
        return Reservation.builder()
                .member(member)
                .plantId(this.plantId)
                .count(this.count)
                .price(this.price)
                .build();
    }
}

