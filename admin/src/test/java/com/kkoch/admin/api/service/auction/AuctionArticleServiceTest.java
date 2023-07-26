package com.kkoch.admin.api.service.auction;

import com.kkoch.admin.IntegrationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
class AuctionArticleServiceTest extends IntegrationTestSupport {

    @Autowired
    private AuctionArticleService auctionArticleService;

    @DisplayName("[경매품 등록]")
    @Test
    void addAuctionArticle() {
        //given


        //when

        //then

    }

}