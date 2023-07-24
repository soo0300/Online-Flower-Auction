package com.kkoch.user.api.controller.member;

import com.kkoch.user.ControllerTestSupport;
import com.kkoch.user.api.controller.member.request.JoinMemberRequest;
import com.kkoch.user.api.service.member.MemberService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MemberControllerTest extends ControllerTestSupport {

    @MockBean
    private MemberService memberService;

    @DisplayName("회원가입 컨트롤러 테스트")
    @Test
    @WithMockUser
    void joinMember() throws Exception {
        //given
        JoinMemberRequest member = JoinMemberRequest.builder()
                .email("test@test.net")
                .loginPw("1234")
                .name("hong")
                .tel("010-1234-5678")
                .businessNumber("A1234512345B")
                .file(null)
                .build();


        // whem, then
        mockMvc.perform(
                        post("/user-service/user/join")
                                .content(objectMapper.writeValueAsString(member))
                                .contentType(MediaType.APPLICATION_JSON)
                                // 403 error, csrf를 같이 보낸다.
                                .with(csrf())
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("SUCCESS"))
                .andExpect(jsonPath("$.data").isNumber());

    }

}