package com.kkoch.user.docs.member;

import com.kkoch.user.api.controller.member.MemberController;
import com.kkoch.user.api.controller.member.request.LoginRequest;
import com.kkoch.user.api.controller.member.response.MemberInfoResponse;
import com.kkoch.user.api.service.member.MemberQueryService;
import com.kkoch.user.api.service.member.MemberService;
import com.kkoch.user.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.head;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class MemberControllerDocsTest extends RestDocsSupport {

    private final MemberService memberService = mock(MemberService.class);
    private final MemberQueryService memberQueryService = mock(MemberQueryService.class);

    @Override
    protected Object initController() {
        return new MemberController(memberService, memberQueryService);
    }

    @DisplayName("회원 정보 조회 API")
    @Test
    void getMyInfo() throws Exception {
        MemberInfoResponse response = MemberInfoResponse.builder()
            .email("ssafy@ssafy.com")
            .name("김싸피")
            .tel("010-1234-1234")
            .businessNumber("123-12-12345")
            .build();

        given(memberQueryService.getMyInfo(anyString()))
            .willReturn(response);

        mockMvc.perform(get("/{memberKey}", UUID.randomUUID().toString())
                .header("Authorization", "token"))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("my-info",
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
                    fieldWithPath("data.email").type(JsonFieldType.STRING)
                        .description("사용자 이메일"),
                    fieldWithPath("data.name").type(JsonFieldType.STRING)
                        .description("사용자 이름"),
                    fieldWithPath("data.tel").type(JsonFieldType.STRING)
                        .description("사용자 연락처"),
                    fieldWithPath("data.businessNumber").type(JsonFieldType.STRING)
                        .description("사용자 사업자번호")
                )
            ));
    }
}
