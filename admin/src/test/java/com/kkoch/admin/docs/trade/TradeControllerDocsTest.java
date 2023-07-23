package com.kkoch.admin.docs.trade;

import com.kkoch.admin.api.controller.trade.TradeController;
import com.kkoch.admin.api.controller.trade.request.AddTradeRequest;
import com.kkoch.admin.api.controller.trade.request.AuctionArticleRequest;
import com.kkoch.admin.api.controller.trade.response.TradeResponse;
import com.kkoch.admin.api.service.trade.TradeQueryService;
import com.kkoch.admin.api.service.trade.TradeService;
import com.kkoch.admin.docs.RestDocsSupport;
import com.kkoch.admin.domain.trade.repository.dto.TradeSearchCond;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TradeControllerDocsTest extends RestDocsSupport {

    private final TradeService tradeService = mock(TradeService.class);
    private final TradeQueryService tradeQueryService = mock(TradeQueryService.class);

    @Override
    protected Object initController() {
        return new TradeController(tradeService, tradeQueryService);
    }

    @DisplayName("낙찰 내역을 등록하는 API")
    @Test
    void addTrade() throws Exception {
        AuctionArticleRequest article1 = createAuctionArticleRequest(1L, 3000);
        AuctionArticleRequest article2 = createAuctionArticleRequest(2L, 4000);
        AuctionArticleRequest article3 = createAuctionArticleRequest(3L, 5000);
        List<AuctionArticleRequest> articles = List.of(article1, article2, article3);

        AddTradeRequest request = AddTradeRequest.builder()
            .memberId(1L)
            .articles(articles)
            .build();

        given(tradeService.addTrade(anyLong(), anyList()))
            .willReturn(4L);

        mockMvc.perform(post("/admin-service/trades")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("trade-create",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("memberId").type(JsonFieldType.NUMBER)
                        .description("회원 PK"),
                    fieldWithPath("articles").type(JsonFieldType.ARRAY)
                        .description("경매품 리스트"),
                    fieldWithPath("articles[].auctionArticleId").type(JsonFieldType.NUMBER)
                        .description("경매품 PK"),
                    fieldWithPath("articles[].bidPrice").type(JsonFieldType.NUMBER)
                        .description("낙찰 가격"),
                    fieldWithPath("articles[].bidTime").type(JsonFieldType.ARRAY)
                        .description("낙찰 시간")

                ),
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.NUMBER)
                        .description("코드"),
                    fieldWithPath("status").type(JsonFieldType.STRING)
                        .description("상태"),
                    fieldWithPath("message").type(JsonFieldType.STRING)
                        .description("메시지"),
                    fieldWithPath("data").type(JsonFieldType.NUMBER)
                        .description("응답 데이터")
                )
            ));
    }

    @DisplayName("낙찰 내역을 조회하는 API")
    @Test
    void getMyTrades() throws Exception {
        TradeResponse tradeResponse1 = createTradeResponse(1L, 10000, LocalDate.of(2023, 7, 10).atStartOfDay(), true, 10);
        TradeResponse tradeResponse2 = createTradeResponse(2L, 20000, LocalDate.of(2023, 7, 17).atStartOfDay(), true, 20);
        TradeResponse tradeResponse3 = createTradeResponse(3L, 30000, LocalDate.of(2023, 7, 24).atStartOfDay(), false, 30);
        List<TradeResponse> responses = List.of(tradeResponse1, tradeResponse2, tradeResponse3);
        PageRequest pageRequest = PageRequest.of(0, 20);

        given(tradeQueryService.getMyTrades(anyLong(), any(TradeSearchCond.class), any(Pageable.class)))
            .willReturn(new PageImpl<>(responses, pageRequest, 100));

        mockMvc.perform(get("/admin-service/trades/{memberId}", 1L)
                .param("term", "1")
                .param("page", "0")
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("trade-search",
                preprocessResponse(prettyPrint()),
                requestParameters(
                    parameterWithName("term")
                        .optional()
                        .description("기간"),
                    parameterWithName("page")
                        .description("페이지")
                ),
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.NUMBER)
                        .description("코드"),
                    fieldWithPath("status").type(JsonFieldType.STRING)
                        .description("상태"),
                    fieldWithPath("message").type(JsonFieldType.STRING)
                        .description("메시지"),
                    fieldWithPath("data").type(JsonFieldType.OBJECT)
                        .description("응답 데이터"),
                    fieldWithPath("data.content").type(JsonFieldType.ARRAY)
                        .description("낙찰 내역 데이터"),
                    fieldWithPath("data.content[].tradeId").type(JsonFieldType.NUMBER)
                        .description("낙찰 내역 PK"),
                    fieldWithPath("data.content[].totalPrice").type(JsonFieldType.NUMBER)
                        .description("총 거래 가격"),
                    fieldWithPath("data.content[].tradeDate").type(JsonFieldType.STRING)
                        .description("거래 시간"),
                    fieldWithPath("data.content[].pickupStatus").type(JsonFieldType.BOOLEAN)
                        .description("픽업여부"),
                    fieldWithPath("data.content[].count").type(JsonFieldType.NUMBER)
                        .description("낙찰한 경매품 갯수"),
                    fieldWithPath("data.pageable").type(JsonFieldType.OBJECT)
                        .description("응답 데이터"),
                    fieldWithPath("data.pageable.sort").type(JsonFieldType.OBJECT)
                        .description("응답 데이터"),
                    fieldWithPath("data.pageable.sort.empty").type(JsonFieldType.BOOLEAN)
                        .description("응답 데이터"),
                    fieldWithPath("data.pageable.sort.sorted").type(JsonFieldType.BOOLEAN)
                        .description("응답 데이터"),
                    fieldWithPath("data.pageable.sort.unsorted").type(JsonFieldType.BOOLEAN)
                        .description("응답 데이터"),
                    fieldWithPath("data.pageable.offset").type(JsonFieldType.NUMBER)
                        .description("응답 데이터"),
                    fieldWithPath("data.pageable.pageNumber").type(JsonFieldType.NUMBER)
                        .description("응답 데이터"),
                    fieldWithPath("data.pageable.pageSize").type(JsonFieldType.NUMBER)
                        .description("응답 데이터"),
                    fieldWithPath("data.pageable.paged").type(JsonFieldType.BOOLEAN)
                        .description("응답 데이터"),
                    fieldWithPath("data.pageable.unpaged").type(JsonFieldType.BOOLEAN)
                        .description("응답 데이터"),
                    fieldWithPath("data.totalPages").type(JsonFieldType.NUMBER)
                        .description("총 페이지 수"),
                    fieldWithPath("data.totalElements").type(JsonFieldType.NUMBER)
                        .description("DB의 전체 데이터 갯수"),
                    fieldWithPath("data.last").type(JsonFieldType.BOOLEAN)
                        .description("마지막 페이지라면 true"),
                    fieldWithPath("data.size").type(JsonFieldType.NUMBER)
                        .description("페이지 당 나타낼 수 있는 데이터의 갯수"),
                    fieldWithPath("data.sort").type(JsonFieldType.OBJECT)
                        .description("응답 데이터"),
                    fieldWithPath("data.sort.empty").type(JsonFieldType.BOOLEAN)
                        .description("응답 데이터"),
                    fieldWithPath("data.sort.sorted").type(JsonFieldType.BOOLEAN)
                        .description("응답 데이터"),
                    fieldWithPath("data.sort.unsorted").type(JsonFieldType.BOOLEAN)
                        .description("응답 데이터"),
                    fieldWithPath("data.number").type(JsonFieldType.NUMBER)
                        .description("현재 페이지 번호"),
                    fieldWithPath("data.numberOfElements").type(JsonFieldType.NUMBER)
                        .description("실제 데이터의 갯수"),
                    fieldWithPath("data.first").type(JsonFieldType.BOOLEAN)
                        .description("첫번째 페이지이면 true"),
                    fieldWithPath("data.empty").type(JsonFieldType.BOOLEAN)
                        .description("리스트가 비어있는지 여부")

                )
            ));
    }

    private AuctionArticleRequest createAuctionArticleRequest(Long auctionArticleId, int bidPrice) {
        return AuctionArticleRequest.builder()
            .auctionArticleId(auctionArticleId)
            .bidPrice(bidPrice)
            .bidTime(LocalDateTime.now())
            .build();
    }

    private TradeResponse createTradeResponse(long tradeId, int totalPrice, LocalDateTime tradeDate, boolean pickupStatus, int count) {
        return TradeResponse.builder()
            .tradeId(tradeId)
            .totalPrice(totalPrice)
            .tradeDate(tradeDate)
            .pickupStatus(pickupStatus)
            .count(count)
            .build();
    }
}
