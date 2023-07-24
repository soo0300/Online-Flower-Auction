package com.kkoch.user.api.controller.member;

import com.kkoch.user.ControllerTestSupport;
import com.kkoch.user.api.controller.member.request.JoinMemberRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MemberControllerTest extends ControllerTestSupport {

    @Test
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

        //when

        //then
        mockMvc.perform(
                        post("/user-service/join")
                                .content(objectMapper.writeValueAsString(member))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("SUCCESS"))
                .andExpect(jsonPath("$.data").isNumber());

    }

}