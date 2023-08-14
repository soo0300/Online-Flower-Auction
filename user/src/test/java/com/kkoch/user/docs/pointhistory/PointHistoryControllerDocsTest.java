package com.kkoch.user.docs.pointhistory;

import com.kkoch.user.api.controller.pointhistory.PointHistoryController;
import com.kkoch.user.api.controller.pointhistory.response.PointHistoryResponse;
import com.kkoch.user.api.service.pointhistory.PointHistoryQueryService;
import com.kkoch.user.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PointHistoryControllerDocsTest extends RestDocsSupport {

    private final PointHistoryQueryService pointHistoryQueryService = mock(PointHistoryQueryService.class);

    @Override
    protected Object initController() {
        return new PointHistoryController(pointHistoryQueryService);
    }

    @DisplayName("포인트 내역 조회 API")
    @Test
    void getMyPointHistories() throws Exception {
        PointHistoryResponse response1 = createPointHistoryResponse();
        PointHistoryResponse response2 = createPointHistoryResponse();
        List<PointHistoryResponse> content = List.of(response1, response2);
        PageRequest pageRequest = PageRequest.of(0, 10);

        given(pointHistoryQueryService.getMyPointHistories(anyString(), any(Pageable.class)))
            .willReturn(new PageImpl<>(content, pageRequest, 2));

        mockMvc.perform(
                get("/{memberKey}/points", UUID.randomUUID().toString())
                    .header("Authorization", "token")
                    .param("pageNum", "0")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("point-history-search",
                preprocessResponse(prettyPrint()),
                requestParameters(
                    parameterWithName("pageNum")
                        .description("페이지 번호")
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
                    fieldWithPath("data.content[].no").type(JsonFieldType.NUMBER)
                        .description("번호"),
                    fieldWithPath("data.content[].bank").type(JsonFieldType.STRING)
                        .description("은행명"),
                    fieldWithPath("data.content[].amount").type(JsonFieldType.NUMBER)
                        .description("금액"),
                    fieldWithPath("data.content[].status").type(JsonFieldType.NUMBER)
                        .description("구분(1: 충전, 2: 사용)"),
                    fieldWithPath("data.content[].createdDate").type(JsonFieldType.STRING)
                        .description("등록일"),
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

    private PointHistoryResponse createPointHistoryResponse() {
        return PointHistoryResponse.builder()
            .bank("신한은행")
            .amount(10_000_000)
            .status(1)
            .createdDate(LocalDateTime.of(2023, 8, 1, 15, 0))
            .build();
    }
}
