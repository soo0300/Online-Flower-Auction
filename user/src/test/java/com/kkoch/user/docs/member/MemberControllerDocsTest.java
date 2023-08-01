package com.kkoch.user.docs.member;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kkoch.user.api.controller.member.MemberController;
import com.kkoch.user.api.controller.member.request.JoinMemberRequest;
import com.kkoch.user.api.controller.member.request.LoginMemberRequest;
import com.kkoch.user.api.controller.member.response.TokenResponse;
import com.kkoch.user.api.service.member.MemberService;
import com.kkoch.user.api.service.member.dto.JoinMemberDto;
import com.kkoch.user.api.service.member.dto.LoginMemberDto;
import com.kkoch.user.docs.RestDocsSupport;
import com.kkoch.user.jwt.JwtUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.web.multipart.MultipartFile;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MemberControllerDocsTest extends RestDocsSupport {

    private final MemberService memberService = mock(MemberService.class);
    private final JwtUtil jwtUtil = mock(JwtUtil.class);

    @Override
    protected Object initController() {
        return new MemberController(memberService, jwtUtil);
    }

    @DisplayName("회원 로그인 API")
    @Test
    void loginMember() throws Exception {
        LoginMemberRequest request = LoginMemberRequest.builder()
            .email("ssafy@ssafy.com")
            .loginPw("ssafy1234@")
            .build();

        TokenResponse jwt = new TokenResponse("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c");

        given(memberService.login(any(LoginMemberDto.class)))
            .willReturn(jwt);

        mockMvc.perform(post("/user-service/user/login")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("member-login",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("email").type(JsonFieldType.STRING)
                        .description("회원 이메일"),
                    fieldWithPath("loginPw").type(JsonFieldType.STRING)
                        .description("회원 비밀번호")
                ),
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.NUMBER)
                        .description("코드"),
                    fieldWithPath("status").type(JsonFieldType.STRING)
                        .description("상태"),
                    fieldWithPath("message").type(JsonFieldType.STRING)
                        .description("메시지"),
                    fieldWithPath("data.token").type(JsonFieldType.STRING)
                        .description("JWT 토큰")
                )
            ));
    }
}
