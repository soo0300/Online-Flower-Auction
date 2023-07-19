package com.kkoch.user.api.controller.reservation.response;

import lombok.Data;

@Data
public class ReservationResponse {

    //목록 - 식물 식별키, 단수, 가격

    private Long plant_id;
    private int count;
    private int price;

}
