package com.kkoch.user.api.controller.reservation.request;

import com.kkoch.user.api.service.reservation.dto.EditReservationDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class EditReservationRequest {

    @NonNull
    Integer count;
    @NonNull
    Integer price;

    @Builder
    private EditReservationRequest(Integer count, Integer price) {
        this.count = count;
        this.price = price;
    }

    public EditReservationDto toEditReservationDto() {
        EditReservationDto dto = EditReservationDto.builder()
                .count(count)
                .price(price)
                .build();
        return dto;
    }

}
