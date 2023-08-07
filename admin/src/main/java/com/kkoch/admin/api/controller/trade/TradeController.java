package com.kkoch.admin.api.controller.trade;

import com.kkoch.admin.api.ApiResponse;
import com.kkoch.admin.api.controller.trade.request.AddTradeRequest;
import com.kkoch.admin.api.controller.trade.response.TradeDetailResponse;
import com.kkoch.admin.api.controller.trade.response.TradeResponse;
import com.kkoch.admin.api.service.trade.TradeQueryService;
import com.kkoch.admin.api.service.trade.TradeService;
import com.kkoch.admin.api.service.trade.dto.AddTradeDto;
import com.kkoch.admin.domain.trade.repository.dto.TradeSearchCond;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.MOVED_PERMANENTLY;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin-service/trades")
@Slf4j
public class TradeController {

    private final TradeService tradeService;
    private final TradeQueryService tradeQueryService;

    @PostMapping
    public ApiResponse<Long> addTrade(@Valid @RequestBody AddTradeRequest request) {
        AddTradeDto dto = request.toAddTradeDto();

        Long tradeId = tradeService.addTrade(dto, LocalDateTime.now());

        log.debug("tradeId={}", tradeId);
        return ApiResponse.ok(tradeId);
    }

    @GetMapping("/{memberKey}")
    public ApiResponse<Page<TradeResponse>> getMyTrades(
            @PathVariable String memberKey,
            @RequestParam Integer term,
            @RequestParam(defaultValue = "0") Integer page
    ) {
        TradeSearchCond cond = TradeSearchCond.of(LocalDate.now(), term);
        PageRequest pageRequest = PageRequest.of(page, 20);
        Page<TradeResponse> data = tradeQueryService.getMyTrades(memberKey, cond, pageRequest);
        return ApiResponse.ok(data);
    }

    @GetMapping("/detail/{tradeId}")
    public ApiResponse<TradeDetailResponse> getTrade(@PathVariable Long tradeId) {
        TradeDetailResponse response = tradeQueryService.getTrade(tradeId);
        return ApiResponse.ok(response);
    }

    @PatchMapping("/{tradeId}")
    public ApiResponse<Long> pickup(@PathVariable Long tradeId) {
        Long pickupTradeId = tradeService.pickup(tradeId);
        return ApiResponse.ok(pickupTradeId);
    }

    @DeleteMapping("/{tradeId}")
    public ApiResponse<Long> removeTrade(@PathVariable Long tradeId) {
        Long removedId = tradeService.remove(tradeId);
        return ApiResponse.of(MOVED_PERMANENTLY, "낙찰 내역이 삭제되었습니다.", removedId);
    }
}
