package com.kkoch.user.api.controller.member;

import com.kkoch.user.ControllerTestSupport;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class MemberControllerTest extends ControllerTestSupport {


    @Test
    void joinMember() throws Exception {
        //given

        //when
        //then
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/user-service/join")
                                .content(objectMapper.writeValueAsString(null))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    public class MemberRequest {
        private String email;
        private String loginPw;
        private String name;
        private String tel;
        private String businessNumber;

        public MemberRequest(String email, String loginPw, String name, String tel, String businessNumber) {
            this.email = email;
            this.loginPw = loginPw;
            this.name = name;
            this.tel = tel;
            this.businessNumber = businessNumber;
        }
    }

}