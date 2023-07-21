package com.kkoch.admin.api.controller.trade;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kkoch.admin.ControllerTestSupport;
import com.kkoch.admin.api.controller.trade.request.AddTradeRequest;
import com.kkoch.admin.api.controller.trade.request.AuctionArticleRequest;
import com.kkoch.admin.api.service.trade.TradeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.ws.rs.core.MediaType;
import java.time.LocalDateTime;
import java.util.List;

class TradeControllerTest extends ControllerTestSupport {

    @MockBean
    private TradeService tradeService;

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
                        MockMvcRequestBuilders.post("/user-service/trades")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("200"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("OK"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("SUCCESS"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isNumber());
    }

    private AuctionArticleRequest createAuctionArticleRequest(Long auctionArticleId, int bidPrice) {
        return AuctionArticleRequest.builder()
                .auctionArticleId(auctionArticleId)
                .bidPrice(bidPrice)
                .bidTime(LocalDateTime.now())
                .build();
    }
}