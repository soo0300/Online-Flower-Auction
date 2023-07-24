package com.kkoch.admin.api.controller.trade;

import com.kkoch.admin.ControllerTestSupport;
import com.kkoch.admin.api.controller.trade.request.AddTradeRequest;
import com.kkoch.admin.api.controller.trade.request.AuctionArticleRequest;
import com.kkoch.admin.api.controller.trade.response.TradeDetailResponse;
import com.kkoch.admin.api.controller.trade.response.TradeResponse;
import com.kkoch.admin.api.service.trade.TradeQueryService;
import com.kkoch.admin.api.service.trade.TradeService;
import com.kkoch.admin.domain.trade.repository.dto.TradeSearchCond;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.ws.rs.core.MediaType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class TradeControllerTest extends ControllerTestSupport {

    @MockBean
    private TradeService tradeService;

    @MockBean
    private TradeQueryService tradeQueryService;

    @DisplayName("경매를 완료하면 거래 내역이 등록 된다.")
    @Test
    void addTrade() throws Exception {
        //given
        AuctionArticleRequest article1 = createAuctionArticleRequest(1L, 3000);
        AuctionArticleRequest article2 = createAuctionArticleRequest(2L, 4000);
        AuctionArticleRequest article3 = createAuctionArticleRequest(3L, 5000);
        List<AuctionArticleRequest> articles = List.of(article1, article2, article3);

        AddTradeRequest request = AddTradeRequest.builder()
            .memberId(4L)
            .articles(articles)
            .build();

        //when //then
        mockMvc.perform(
                post("/admin-service/trades")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("200"))
            .andExpect(jsonPath("$.status").value("OK"))
            .andExpect(jsonPath("$.message").value("SUCCESS"))
            .andExpect(jsonPath("$.data").isNumber());
    }

    @DisplayName("회원은 조회 기간과 페이지를 선택하여 본인의 낙찰 내역을 확인할 수 있다.")
    @Test
    void getMyTrades() throws Exception {
        //given
        List<TradeResponse> responses = List.of();
        PageImpl<TradeResponse> tradeResponses = new PageImpl<>(responses);

        BDDMockito.given(tradeQueryService.getMyTrades(anyLong(), any(TradeSearchCond.class), any(Pageable.class)))
            .willReturn(tradeResponses);

        //when //then
        mockMvc.perform(
                get("/admin-service/trades/{memberId}", 1L)
                    .queryParam("term", "1")
                    .queryParam("page", "0")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("200"))
            .andExpect(jsonPath("$.status").value("OK"))
            .andExpect(jsonPath("$.message").value("SUCCESS"))
            .andExpect(jsonPath("$.data.content").isArray());
    }

    @DisplayName("회원은 본인의 낙찰 내역을 상세조회할 수 있다.")
    @Test
    void getTrade() throws Exception {
        //given
        TradeDetailResponse response = TradeDetailResponse.builder()
            .totalPrice(10000)
            .tradeTime(LocalDate.of(2023, 7, 10).atStartOfDay())
            .status(false)
            .build();

        BDDMockito.given(tradeQueryService.getTrade(anyLong()))
            .willReturn(response);

        //when //then
        mockMvc.perform(
                get("/admin-service/trades/detail/{tradeId}", 1L)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("200"))
            .andExpect(jsonPath("$.status").value("OK"))
            .andExpect(jsonPath("$.message").value("SUCCESS"))
            .andExpect(jsonPath("$.data.totalPrice").value(10000))
            .andExpect(jsonPath("$.data.status").value(false));
    }

    @DisplayName("회원이 이미 픽업한 상품이면 400 에러를 발생시킨다.")
    @Test
    void pickupWithException() throws Exception {
        //given
        BDDMockito.given(tradeService.pickup(anyLong()))
                .willThrow(new IllegalArgumentException("이미 픽업한 상품입니다."));

        //when //then
        mockMvc.perform(
                        patch("/admin-service/trades/{tradeId}", 1L)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("이미 픽업한 상품입니다."))
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @DisplayName("회원은 본인이 낙찰받은 경매품을 픽업할 수 있다.")
    @Test
    void pickup() throws Exception {
        //given
        BDDMockito.given(tradeService.pickup(anyLong()))
                .willReturn(1L);

        //when //then
        mockMvc.perform(
                        patch("/admin-service/trades/{tradeId}", 1L)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("SUCCESS"))
                .andExpect(jsonPath("$.data").isNumber());
    }

    @DisplayName("회원은 본인의 낙찰 내역을 삭제할 수 있다.")
    @Test
    void removeTrade() throws Exception {
        //given
        BDDMockito.given(tradeService.remove(anyLong()))
                .willReturn(1L);

        //when //then
        mockMvc.perform(
                delete("/admin-service/trades/{tradeId}", 1L)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("301"))
                .andExpect(jsonPath("$.status").value("MOVED_PERMANENTLY"))
                .andExpect(jsonPath("$.message").value("낙찰 내역이 삭제되었습니다."))
                .andExpect(jsonPath("$.data").isNumber());

    }

    private AuctionArticleRequest createAuctionArticleRequest(Long auctionArticleId, int bidPrice) {
        return AuctionArticleRequest.builder()
            .auctionArticleId(auctionArticleId)
            .bidPrice(bidPrice)
            .bidTime(LocalDateTime.now())
            .build();
    }
}