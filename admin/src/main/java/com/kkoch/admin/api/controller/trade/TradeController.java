package com.kkoch.admin.api.controller.trade;

import com.kkoch.admin.api.ApiResponse;
import com.kkoch.admin.api.controller.trade.response.TradeDetailResponse;
import com.kkoch.admin.api.controller.trade.response.TradeResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.MOVED_PERMANENTLY;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user-service/trades")
@Slf4j
@Api(tags = {"낙찰 내역 기능"})
public class TradeController {

    //낙찰 내역 등록

    //낙찰 내역 조회
    @ApiOperation(value = "낙찰 내역 조회")
    @GetMapping
    public ApiResponse<List<TradeResponse>> getTrades(
            @RequestParam Integer startTime,
            @RequestParam Long code,
            @RequestParam Long name,
            @RequestParam Long type
    ) {
        return ApiResponse.ok(new ArrayList<>());
    }

    //낙찰 내역 상세 조회
    @ApiOperation(value = "낙찰 내역 상세 조회")
    @GetMapping("/{tradeId}")
    public ApiResponse<TradeDetailResponse> getTrade(@PathVariable Long tradeId) {
        return ApiResponse.ok(null);
    }

    //낙찰 내역 수정 - 픽업여부
    @ApiOperation(value = "낙찰 픽업여부 변경")
    @PatchMapping("/{tradeId}")
    public ApiResponse<?> pickup(@PathVariable Long tradeId) {
        return ApiResponse.ok(null);
    }

    //낙찰 내역 삭제
    @ApiOperation(value = "낙찰 내역 삭제")
    @DeleteMapping("/{tradeId}")
    public ApiResponse<?> removeTrade(@PathVariable String tradeId) {
        return ApiResponse.of(MOVED_PERMANENTLY, null, null);
    }
}
