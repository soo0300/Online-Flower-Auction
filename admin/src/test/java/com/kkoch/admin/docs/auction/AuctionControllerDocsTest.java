package com.kkoch.admin.docs.auction;

import com.kkoch.admin.api.controller.auction.AuctionController;
import com.kkoch.admin.api.controller.auction.response.AuctionForMemberResponse;
import com.kkoch.admin.api.service.auction.AuctionQueryService;
import com.kkoch.admin.api.service.auction.AuctionService;
import com.kkoch.admin.docs.RestDocsSupport;
import com.kkoch.admin.domain.auction.Status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.List;

import static com.kkoch.admin.domain.auction.Status.*;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuctionControllerDocsTest extends RestDocsSupport {

    private final AuctionService auctionService = mock(AuctionService.class);
    private final AuctionQueryService auctionQueryService = mock(AuctionQueryService.class);

    @Override
    protected Object initController() {
        return new AuctionController(auctionService, auctionQueryService);
    }

    @DisplayName("경매 일정을 조회하는 API")
    @Test
    void getAuctionListForMember() throws Exception {
        AuctionForMemberResponse response1 = createAuctionForMemberResponse(1L, "23. 7. 10. 오전 5:00", OPEN, 1);

        BDDMockito.given(auctionQueryService.getAuctionForMember())
            .willReturn(response1);

        mockMvc.perform(get("/admin-service/auctions/api"))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("auction-search",
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.NUMBER)
                        .description("코드"),
                    fieldWithPath("status").type(JsonFieldType.STRING)
                        .description("상태"),
                    fieldWithPath("message").type(JsonFieldType.STRING)
                        .description("메시지"),
                    fieldWithPath("data").type(JsonFieldType.OBJECT)
                        .description("응답 데이터"),
                    fieldWithPath("data.auctionId").type(JsonFieldType.NUMBER)
                        .description("경매 일정 PK"),
                    fieldWithPath("data.title").type(JsonFieldType.STRING)
                        .description("경매 타이틀"),
                    fieldWithPath("data.status").type(JsonFieldType.STRING)
                        .description("경매 상태"),
                    fieldWithPath("data.code").type(JsonFieldType.NUMBER)
                        .description("경매 구분 코드")
                )
            ));
    }

    private AuctionForMemberResponse createAuctionForMemberResponse(long auctionId, String title, Status status, int code) {
        return AuctionForMemberResponse.builder()
            .auctionId(auctionId)
            .title(title)
            .status(status)
            .code(code)
            .build();
    }
}
