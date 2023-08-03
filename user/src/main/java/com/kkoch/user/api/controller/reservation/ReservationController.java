package com.kkoch.user.api.controller.reservation;

import com.kkoch.user.api.controller.ApiResponse;
import com.kkoch.user.api.controller.reservation.request.AddReservationRequest;
import com.kkoch.user.api.controller.reservation.response.ReservationResponse;
import com.kkoch.user.api.service.reservation.ReservationService;
import com.kkoch.user.api.service.reservation.dto.AddReservationDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.MOVED_PERMANENTLY;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user-service/reservations")
@Slf4j
@Api(tags = {"거래 예약 기능"})
public class ReservationController {

    private final ReservationService reservationService;

    @ApiOperation(value = "거래 예약 등록")
    @PostMapping
    public ApiResponse<Long> addReservation(@Valid @RequestBody AddReservationRequest request) {

//        String loginId = jwtUtil.getEmailByJWT();
//        AddReservationDto dto = request.toAddReservationDto();
//        Long reservationId = reservationService.addReservation(loginId, dto);
//        return ApiResponse.ok(reservationId);
        return null;
    }

    @ApiOperation(value = "거래 예약 조회")
    @GetMapping
    public ApiResponse<ReservationResponse> getMyReservation() {
//        String loginId = jwtUtil.getEmailByJWT();
//        ReservationResponse response = reservationService.getReservation(loginId);
//        return ApiResponse.ok(response);
        return null;
    }

    @ApiOperation(value = "예약 단수 및 가격 변경")
    @PatchMapping("/{reservationId}")
    public ApiResponse<?> editReservation(@PathVariable Long reservationId) {
        return ApiResponse.of(MOVED_PERMANENTLY, "예약 변경", null);
    }

    @ApiOperation(value = "거래 예약 삭제")
    @DeleteMapping("/{reservationId}")
    public ApiResponse<Long> removeReservation(@PathVariable Long reservationId) {
        Long savedId = reservationService.removeReservation(reservationId);
        return ApiResponse.ok(savedId);
    }
}


