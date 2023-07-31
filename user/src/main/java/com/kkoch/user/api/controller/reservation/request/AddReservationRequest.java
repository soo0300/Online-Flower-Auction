package com.kkoch.user.api.controller.reservation.request;

import com.kkoch.user.api.service.reservation.dto.AddReservationDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class AddReservationRequest {

    @NonNull
    private Long memberId;
    @NonNull
    private Long plantId;
    @NonNull
    private int count;
    @NonNull
    private int price;

    @Builder
    public AddReservationRequest(@NonNull Long memberId, @NonNull Long plantId, @NonNull int count, @NonNull int price) {
        this.memberId = memberId;
        this.plantId = plantId;
        this.count = count;
        this.price = price;
    }

    public AddReservationDto toAddReservationDto() {
        AddReservationDto dto = AddReservationDto.builder()
                .memberId(memberId)
                .plantId(plantId)
                .count(count)
                .price(price)
                .build();
        return dto;
    }
}
