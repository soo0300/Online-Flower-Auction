package com.kkoch.admin.api.controller.admin;

import com.kkoch.admin.ControllerTestSupport;
import com.kkoch.admin.api.controller.admin.request.AddAdminRequest;
import com.kkoch.admin.api.controller.admin.request.EditAdminRequest;
import com.kkoch.admin.api.controller.admin.request.LoginRequest;
import com.kkoch.admin.api.service.admin.AdminService;
import com.kkoch.admin.api.service.admin.dto.EditAdminDto;
import com.kkoch.admin.api.service.admin.dto.LoginDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {AdminController.class})
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
                .build();
        // when
        // then
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
    }

    @DisplayName("관계자 목록을 조회할 수 있다.")
    @Test
    public void getAdmin() throws Exception {
        // given
        // when
        // then
        mockMvc.perform(
                        get("/admin-service/admin")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("SUCCESS"))
                .andExpect(jsonPath("$.data").isArray());
    }

    @DisplayName("관계자의 비밀번호와 전화번호를 변경할 수 있다")
    @Test
    public void setAdmin() throws Exception {
        // given
        EditAdminRequest editAdmin = EditAdminRequest.builder()
                .tel("010-1234-5678")
                .loginPw("ssafy1234!@#")
                .build();

        EditAdminDto dto = editAdmin.toEditAdminDto();

        BDDMockito.given(adminService.setAdmin(1L, dto)
        ).willReturn(1L);

        //when //then
        mockMvc.perform(
                        patch("/admin-service/admin/{adminId}", 1L)
                                .content(objectMapper.writeValueAsString(editAdmin))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("SUCCESS"))
                .andExpect(jsonPath("$.data").isNumber());
    }

    @DisplayName("관계자는 관계자 정보를 삭제할 수 있다 .")
    @Test
    public void remove() throws Exception {
        // given
        BDDMockito.given(adminService.removeAdmin(anyLong()))
                .willReturn(1L);
        // when
        // then
        mockMvc.perform(
                        delete("/admin-service/admin/{adminId}", 1L)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("301"))
                .andExpect(jsonPath("$.status").value("MOVED_PERMANENTLY"))
                .andExpect(jsonPath("$.message").value("관계자 정보가 삭제되었습니다."))
                .andExpect(jsonPath("$.data").isNumber());

    }

    @DisplayName("관계자는 맞지 않는 정보를 입력할 시 로그인에 실패한다.")
    @Test
    public void loginAdminFail() throws Exception {
        // given
        BDDMockito.given(adminService.loginAdmin(any(LoginDto.class)))
                .willThrow(new IllegalArgumentException("아이디와 비밀번호를 확인하세요"));

        LoginRequest request = LoginRequest.builder()
                .loginId("123")
                .loginPw("345")
                .build();

        // when
        // then
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/admin-service/admin/login")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("아이디와 비밀번호를 확인하세요"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty());
    }

    @DisplayName("관계자는 올바른 입력 시 로그인에 성공한다.")
    @Test
    public void loginAdminSuccess() throws Exception {
        // given
        BDDMockito.given(adminService.loginAdmin(any(LoginDto.class)))
                .willReturn(LoginAdmin.builder()
                        .id(1L)
                        .authority("50")
                        .build());

        LoginRequest request = LoginRequest.builder()
                .loginId("123")
                .loginPw("345")
                .build();

        // when
        // then
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/admin-service/admin/login")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("OK"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("SUCCESS"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isString());
    }

    private MockHttpSession getLoginAdminSession() {
        MockHttpSession session = new MockHttpSession();
        LoginAdmin loginAdmin = LoginAdmin.builder()
                .id(1L)
                .authority("10")
                .build();
        session.setAttribute("loginAdmin", loginAdmin);
        return session;
    }
}