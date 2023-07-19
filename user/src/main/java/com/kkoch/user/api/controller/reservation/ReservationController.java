package com.kkoch.user.api.controller.reservation;

import com.kkoch.user.api.controller.ApiResponse;
import com.kkoch.user.api.controller.reservation.request.EditReservation;
import com.kkoch.user.api.controller.reservation.request.MakeReservationRequest;
import com.kkoch.user.api.controller.reservation.response.ReservationResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.MOVED_PERMANENTLY;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user-service/reservations")
@Slf4j
@Api(tags = {"거래 예약 기능"})
public class ReservationController {
    @ApiOperation(value="거래 예약 등록")
    @PostMapping
    public ApiResponse<?> addReservation(@RequestBody MakeReservationRequest request){
        return null;
    }

    @ApiOperation(value="거래 예약 조회")
    @GetMapping
    public ApiResponse<List<ReservationResponse>> getMyReservation(){
        return ApiResponse.ok(null);
    }

    @ApiOperation(value= "예약 단수 및 가격 변경")
    @PatchMapping()
    public ApiResponse<?> editReservation(@RequestBody EditReservation request){
        return ApiResponse.of(MOVED_PERMANENTLY,"예약 변경",null);
    }

    @ApiOperation(value="거래 예약 삭제")
    @DeleteMapping("/{reservationId}")
    public ApiResponse<?> removeReservation(@PathVariable Long reservationId){
        return ApiResponse.of(MOVED_PERMANENTLY,"거래 예약 삭제",null);
    }
}


