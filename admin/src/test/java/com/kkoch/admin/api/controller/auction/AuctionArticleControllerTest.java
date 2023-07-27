package com.kkoch.admin.api.controller.auction;


import com.kkoch.admin.ControllerTestSupport;
import com.kkoch.admin.api.controller.auction.request.AddAuctionArticleRequest;
import com.kkoch.admin.api.service.auction.AuctionArticleService;
import com.kkoch.admin.api.service.auction.dto.AddAuctionArticleDto;
import com.kkoch.admin.domain.Grade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@WebMvcTest(controllers = {AuctionArticleController.class})
class AuctionArticleControllerTest extends ControllerTestSupport {

    @MockBean
    private AuctionArticleService auctionArticleService;

    @DisplayName("[경매품 등록] 존재하지 않는 경매일정에 경매품을 등록하면 예외가 발생한다.")
    @Test
    void addAuctionArticleScheduleError() throws Exception {
        //given
        AddAuctionArticleRequest request = generateAddAuctionArticleRequest();

        BDDMockito.given(auctionArticleService.addAuctionArticle(anyLong(), anyLong(), any(AddAuctionArticleDto.class)))
                .willThrow(new NoSuchElementException("존재하지 않는 경매 일정입니다."));

        //when
        //then
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/admin-service/auction-articles")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("존재하지 않는 경매 일정입니다."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty());
    }


    @DisplayName("[경매품 등록] 존재하지 않는 식물을 경매품으로 등록하면 예외가 발생한다.")
    @Test
    void addAuctionArticlePlantError() throws Exception {
        //given
        AddAuctionArticleRequest request = generateAddAuctionArticleRequest();

        BDDMockito.given(auctionArticleService.addAuctionArticle(anyLong(), anyLong(), any(AddAuctionArticleDto.class)))
                .willThrow(new NoSuchElementException("존재하지 않는 식물입니다."));

        //when
        //then
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/admin-service/auction-articles")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("존재하지 않는 식물입니다."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty());
    }

    @DisplayName("[경매품 등록]")
    @Test
    void addAuctionArticle() throws Exception {
        //given
        AddAuctionArticleRequest request = generateAddAuctionArticleRequest();

        //when
        //then
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/admin-service/auction-articles")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("OK"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("SUCCESS"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isNumber());
    }

    private static AddAuctionArticleRequest generateAddAuctionArticleRequest() {
        return AddAuctionArticleRequest.builder()
                .auctionId(1L)
                .plantId(1L)
                .startPrice(20000)
                .count(20)
                .grade(Grade.NONE)
                .shipper("꽃파파")
                .region("광주")
                .build();
    }
}