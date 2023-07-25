package com.kkoch.admin.api.controller.admin;

import com.kkoch.admin.ControllerTestSupport;
import com.kkoch.admin.api.controller.admin.request.AddAdminRequest;
import com.kkoch.admin.api.service.admin.AdminService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminControllerTest extends ControllerTestSupport {

    @MockBean
    private AdminService adminService;

    @DisplayName("관계자 정보를 입력받아서 관계자 등록 성공 ")
    @Test
    public void addAdminTest() throws Exception {
        // given
        AddAdminRequest admin = AddAdminRequest.builder()
                .loginId("soo")
                .loginPw("ssafy1234")
                .name("soojin")
                .tel("010-2034-2034")
                .position("30")
                .active(true)
                .build();
        // when
        mockMvc.perform(
                post("/admin-service/admin")
                        .content(objectMapper.writeValueAsString(admin))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("SUCCESS"))
                .andExpect(jsonPath("$.data").isNumber());

        // then
    }
}