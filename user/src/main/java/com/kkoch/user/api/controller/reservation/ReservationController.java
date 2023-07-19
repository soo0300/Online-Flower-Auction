package com.kkoch.user.api.controller.reservation;

import com.kkoch.user.api.controller.ApiResponse;
import com.kkoch.user.api.controller.member.request.WithdrawalRequest;
import com.kkoch.user.api.controller.reservation.request.EditCount;
import com.kkoch.user.api.controller.reservation.request.EditPrice;
import com.kkoch.user.api.controller.reservation.request.MakeReservationRequest;
import com.kkoch.user.api.controller.reservation.response.ReservationResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import static org.springframework.http.HttpStatus.MOVED_PERMANENTLY;


@RequiredArgsConstructor
@RestController
@RequestMapping("/reservation-service")
@Slf4j
@Api(tags = {"거래 예약 기능"})

public class ReservationController {

    //꽃의 예약을 등록할 수 있다.
    //예약 내역을 조회할 수 있다.
    //예약 내역을 수정할 수 있다.
    //예약 내역을 삭제할 수 있다.

    @ApiOperation(value="거래 예약 등록")
    @PostMapping("/make-reserve")
    public ApiResponse<?> joinMember(@RequestBody MakeReservationRequest request){
        return null;
    }

    @ApiOperation(value="거래 예약 조회")
    @GetMapping("my-reserve")
    public ApiResponse<ReservationResponse> getMyReservation(){
        return ApiResponse.ok(null);
    }

    @ApiOperation(value= "예약 단 수 변경")
    @PatchMapping("my-count")
    public ApiResponse<?> editMyCount(@RequestBody EditCount request){
        return ApiResponse.of(MOVED_PERMANENTLY,"예약 변경",null);
    }

    @ApiOperation(value="예약 가격 변경")
    @PatchMapping("my-price")
    public ApiResponse<?> editMyPrice(@RequestBody EditPrice request){
        return ApiResponse.of(MOVED_PERMANENTLY,"에약 변경",null);
    }


    //거래 예약 삭제
    @ApiOperation(value="거래 예약 삭제")
    @DeleteMapping("/my-reserve")
    public ApiResponse<?> withdrawal(@RequestBody WithdrawalRequest request){
        return ApiResponse.of(MOVED_PERMANENTLY,"거래 예약 삭제",null);
    }

}


