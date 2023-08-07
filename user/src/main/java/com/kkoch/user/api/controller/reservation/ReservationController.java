package com.kkoch.user.api.controller.reservation;

import com.kkoch.user.api.controller.ApiResponse;
import com.kkoch.user.api.controller.reservation.request.AddReservationRequest;
import com.kkoch.user.api.controller.reservation.response.ReservationResponse;
import com.kkoch.user.api.service.reservation.ReservationService;
import com.kkoch.user.api.service.reservation.dto.AddReservationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/{memberKey}/reservations")
@Slf4j
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ApiResponse<ReservationResponse> addReservation(
        @Valid @RequestBody AddReservationRequest request,
        @PathVariable String memberKey
    ) {
        AddReservationDto dto = request.toAddReservationDto();
        ReservationResponse response = reservationService.addReservation(memberKey, dto);

        return ApiResponse.ok(response);
    }
}


