package com.kkoch.admin.api.controller.auction;

import com.kkoch.admin.ControllerTestSupport;
import com.kkoch.admin.api.controller.admin.LoginAdmin;
import com.kkoch.admin.api.controller.auction.request.AddAuctionRequest;
import com.kkoch.admin.api.service.auction.AuctionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;


class AuctionControllerTest extends ControllerTestSupport {

    @MockBean
    private AuctionService auctionService;

    @Test
    @DisplayName("경매일정 등록 시 현재시간부터 1시간 이내에 등록하면 에러가 발생한다.")
    void addAuctionTimeException() throws Exception {
        //given
        AddAuctionRequest request = new AddAuctionRequest(LocalDateTime.now().plusMinutes(30), 1);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("loginAdmin", new LoginAdmin());

        //when //then
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/admin-service/auctions")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                                .session(session)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(400));
    }

    @Test
    @DisplayName("경매일정 등록 성공")
    void addAuction() throws Exception {
        //given
        AddAuctionRequest request = new AddAuctionRequest(LocalDateTime.now().plusHours(2), 1);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("loginAdmin", new LoginAdmin());

        //when //then
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/admin-service/auctions")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                                .session(session)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}