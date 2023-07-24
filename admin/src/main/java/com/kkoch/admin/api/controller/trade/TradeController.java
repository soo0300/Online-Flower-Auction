package com.kkoch.admin.api.controller.trade;

import com.kkoch.admin.api.ApiResponse;
import com.kkoch.admin.api.controller.trade.request.AddTradeRequest;
import com.kkoch.admin.api.controller.trade.request.AuctionArticleRequest;
import com.kkoch.admin.api.controller.trade.response.TradeDetailResponse;
import com.kkoch.admin.api.controller.trade.response.TradeResponse;
import com.kkoch.admin.api.service.trade.TradeQueryService;
import com.kkoch.admin.api.service.trade.TradeService;
import com.kkoch.admin.api.service.trade.dto.AddTradeDto;
import com.kkoch.admin.domain.trade.repository.dto.TradeSearchCond;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.MOVED_PERMANENTLY;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin-service/trades")
@Slf4j
@Api(tags = {"낙찰 내역 기능"})
public class TradeController {

    private final TradeService tradeService;
    private final TradeQueryService tradeQueryService;

    @ApiOperation(value = "낙찰 내역 등록")
    @PostMapping
    public ApiResponse<Long> addTrade(@Valid @RequestBody AddTradeRequest request) {
        List<AddTradeDto> dto = request.getArticles().stream()
                .map(AuctionArticleRequest::toAddTradeDto)
                .collect(Collectors.toList());

        Long tradeId = tradeService.addTrade(request.getMemberId(), dto);
        log.debug("tradeId={}", tradeId);
        return ApiResponse.ok(tradeId);
    }

    @ApiOperation(value = "낙찰 내역 조회")
    @GetMapping("/{memberId}")
    public ApiResponse<Page<TradeResponse>> getMyTrades(
            @PathVariable Long memberId,
            @RequestParam Integer term,
            @RequestParam(defaultValue = "0") Integer page
    ) {
        TradeSearchCond cond = TradeSearchCond.of(LocalDate.now(), term);
        PageRequest pageRequest = PageRequest.of(page, 20);
        Page<TradeResponse> data = tradeQueryService.getMyTrades(memberId, cond, pageRequest);
        return ApiResponse.ok(data);
    }

    @ApiOperation(value = "낙찰 내역 상세 조회")
    @GetMapping("/detail/{tradeId}")
    public ApiResponse<TradeDetailResponse> getTrade(@PathVariable Long tradeId) {
        TradeDetailResponse response = tradeQueryService.getTrade(tradeId);
        return ApiResponse.ok(response);
    }

    @ApiOperation(value = "낙찰 픽업여부 변경")
    @PatchMapping("/{tradeId}")
    public ApiResponse<Long> pickup(@PathVariable Long tradeId) {
        Long pickupTradeId;
        try {
            pickupTradeId = tradeService.pickup(tradeId);
        } catch (IllegalArgumentException e) {
            return ApiResponse.of(HttpStatus.BAD_REQUEST, e.getMessage(), null);
        }
        return ApiResponse.ok(pickupTradeId);
    }

    @ApiOperation(value = "낙찰 내역 삭제")
    @DeleteMapping("/{tradeId}")
    public ApiResponse<Long> removeTrade(@PathVariable Long tradeId) {
        Long removedId = tradeService.remove(tradeId);
        return ApiResponse.of(MOVED_PERMANENTLY, "낙찰 내역이 삭제되었습니다.", removedId);
    }
}
