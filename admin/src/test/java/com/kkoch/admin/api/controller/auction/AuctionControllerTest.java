package com.kkoch.admin.api.controller.auction;

import com.kkoch.admin.ControllerTestSupport;
import com.kkoch.admin.api.controller.admin.LoginAdmin;
import com.kkoch.admin.api.controller.auction.request.AddAuctionRequest;
import com.kkoch.admin.api.controller.auction.response.AuctionTitleResponse;
import com.kkoch.admin.api.service.auction.AuctionService;
import com.kkoch.admin.api.service.auction.dto.AddAuctionDto;
import com.kkoch.admin.api.service.auction.dto.SetAuctionStatusDto;
import com.kkoch.admin.domain.admin.Admin;
import com.kkoch.admin.domain.auction.Auction;
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

import java.time.LocalDateTime;

import static com.kkoch.admin.domain.auction.Status.CLOSE;
import static com.kkoch.admin.domain.auction.Status.OPEN;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@WebMvcTest(controllers = {AuctionController.class})
class AuctionControllerTest extends ControllerTestSupport {

    @MockBean
    private AuctionService auctionService;

    @DisplayName("[경매 상태 수정]")
    @Test
    void setStatusError() throws Exception {
        //given
        MockHttpSession session = getLoginAdminSession();

        SetAuctionStatusDto dto = SetAuctionStatusDto.builder()
                .auctionId(3L)
                .status(CLOSE)
                .build();
        AuctionTitleResponse response = AuctionTitleResponse.builder()
                .auctionId(3L)
                .title("title")
                .build();
        BDDMockito.given(auctionService.setStatus(dto))
                .willReturn(response);

        Long auctionId = 3L;
        //when //then
        mockMvc.perform(
                        MockMvcRequestBuilders.patch("/admin-service/auctions/{auctionId}/{status}", auctionId, CLOSE)
                                .session(session)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.title").value("title"));
    }

    @Test
    @DisplayName("[경매일정 등록] 시 현재시간부터 1시간 이내에 등록하면 에러가 발생한다.")
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
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("[경매일정 등록] 성공")
    void addAuction() throws Exception {
        //given
        AuctionTitleResponse response = AuctionTitleResponse.builder()
                .auctionId(1L)
                .title("title")
                .build();
        AddAuctionRequest request = new AddAuctionRequest(LocalDateTime.now().plusHours(2), 1);

        MockHttpSession session = getLoginAdminSession();

        BDDMockito.given(auctionService.addAuction(anyLong(), any(AddAuctionDto.class)))
                .willReturn(response);

        //when //then
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/admin-service/auctions")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                                .session(session)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.title").value("title"));

    }

    private static MockHttpSession getLoginAdminSession() {
        MockHttpSession session = new MockHttpSession();
        LoginAdmin loginAdmin = LoginAdmin.builder()
                .id(1L)
                .authority("10")
                .build();
        session.setAttribute("loginAdmin", loginAdmin);
        return session;
    }
}