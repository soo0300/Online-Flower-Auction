package com.kkoch.admin.docs.auction;

import com.kkoch.admin.api.controller.auction.AuctionArticleController;
import com.kkoch.admin.api.controller.auction.response.AuctionArticleForMemberResponse;
import com.kkoch.admin.api.service.auction.AuctionArticleQueryService;
import com.kkoch.admin.api.service.auction.AuctionArticleService;
import com.kkoch.admin.docs.RestDocsSupport;
import com.kkoch.admin.domain.Grade;
import com.kkoch.admin.domain.auction.repository.dto.AuctionArticleSearchCond;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuctionArticleControllerDocsTest extends RestDocsSupport {

    private final AuctionArticleService auctionArticleService = mock(AuctionArticleService.class);
    private final AuctionArticleQueryService auctionArticleQueryService = mock(AuctionArticleQueryService.class);

    @Override
    protected Object initController() {
        return new AuctionArticleController(auctionArticleService, auctionArticleQueryService);
    }

    @DisplayName("실시간 낙찰 내역 조회 API")
    @Test
    void getAuctionArticleList() throws Exception {
        AuctionArticleForMemberResponse response1 = createAuctionArticleResponse("푸에고", 10, 9600, LocalDateTime.of(2023, 9, 20, 5, 0), "광주");
        AuctionArticleForMemberResponse response2 = createAuctionArticleResponse("빅토리아", 20, 3400, LocalDateTime.of(2023, 9, 19, 5, 0), "인천");
        AuctionArticleForMemberResponse response3 = createAuctionArticleResponse("헤라", 30, 3600, LocalDateTime.of(2023, 9, 18, 5, 0), "광주");
        AuctionArticleForMemberResponse response4 = createAuctionArticleResponse("하젤", 15, 3030, LocalDateTime.of(2023, 9, 17, 5, 0), "서울");
        AuctionArticleForMemberResponse response5 = createAuctionArticleResponse("푸에고", 25, 5500, LocalDateTime.of(2023, 9, 16, 5, 0), "광주");
        AuctionArticleForMemberResponse response6 = createAuctionArticleResponse("헤라", 10, 2400, LocalDateTime.of(2023, 9, 15, 5, 0), "인천");
        AuctionArticleForMemberResponse response7 = createAuctionArticleResponse("하젤", 30, 4920, LocalDateTime.of(2023, 9, 14, 5, 0), "서울");

        List<AuctionArticleForMemberResponse> responses = List.of(response1, response2, response3, response4, response5, response6, response7);

        AuctionArticleSearchCond.of(LocalDateTime.of(2023, 9, 20, 5, 0).toLocalDate(), "절화", "장미", "", "");

        AuctionArticleSearchCond.builder()
                .endDateTime(LocalDateTime.of(2023, 9, 20, 5, 0).toLocalDate())
                .build();

        BDDMockito.given(auctionArticleQueryService.getAuctionArticleListForMember(any(AuctionArticleSearchCond.class), any(Pageable.class))
        ).willReturn(new PageImpl<>(responses, PageRequest.of(0, 15), 100));

        mockMvc.perform(get("/admin-service/auction-articles/api")
                        .param("endDateTime", "2023-09-20")
                        .param("code", "절화")
                        .param("type", "장미")
                        .param("name", "")
                        .param("region", "")
                        .param("page", "0")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("auction-article-search",
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("endDateTime")
                                        .optional()
                                        .description("조회 마지막 날짜"),
                                parameterWithName("code")
                                        .optional()
                                        .description("구분코드"),
                                parameterWithName("type")
                                        .description("품목"),
                                parameterWithName("name")
                                        .description("품종"),
                                parameterWithName("region")
                                        .description("지역"),
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
                                        .description("경매품 데이터"),
                                fieldWithPath("data.content[].code").type(JsonFieldType.STRING)
                                        .description("경매품 구분코드"),
                                fieldWithPath("data.content[].type").type(JsonFieldType.STRING)
                                        .description("경매품 품목"),
                                fieldWithPath("data.content[].name").type(JsonFieldType.STRING)
                                        .description("경매품 품종"),
                                fieldWithPath("data.content[].grade").type(JsonFieldType.STRING)
                                        .description("경매품 등급"),
                                fieldWithPath("data.content[].count").type(JsonFieldType.NUMBER)
                                        .description("경매품 단(속)"),
                                fieldWithPath("data.content[].bidPrice").type(JsonFieldType.NUMBER)
                                        .description("경매품 낙찰 가격"),
                                fieldWithPath("data.content[].bidTime").type(JsonFieldType.STRING)
                                        .description("경매품 낙찰 시간"),
                                fieldWithPath("data.content[].region").type(JsonFieldType.STRING)
                                        .description("경매품 지역"),

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

    private static AuctionArticleForMemberResponse createAuctionArticleResponse(String name, int count, int bidPrice, LocalDateTime bidTime, String region) {
        return AuctionArticleForMemberResponse.builder()
                .code("절화")
                .type("장미")
                .name(name)
                .grade(Grade.NONE)
                .count(count)
                .bidPrice(bidPrice)
                .bidTime(bidTime)
                .region(region)
                .build();
    }
}
