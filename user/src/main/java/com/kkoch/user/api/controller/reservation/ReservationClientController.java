package com.kkoch.user.api.controller.reservation;

import com.kkoch.user.api.controller.reservation.response.ReservationForAuctionResponse;
import com.kkoch.user.api.service.reservation.ReservationQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/reservations")
@Slf4j
public class ReservationClientController {

    private final ReservationQueryService reservationQueryService;

    @GetMapping
    public ReservationForAuctionResponse getReservationForAuction(@RequestParam Long plantId) {
        return reservationQueryService.getReservation(plantId);
    }
}
