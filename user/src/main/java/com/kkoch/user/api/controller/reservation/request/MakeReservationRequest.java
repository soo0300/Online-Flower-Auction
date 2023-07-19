package com.kkoch.user.api.controller.reservation.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MakeReservationRequest {
    private Long plantId;
    private int count;
    private int price;

}
