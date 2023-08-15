package com.kkoch.admin.domain.admin.repository;

import com.kkoch.admin.IntegrationTestSupport;
import com.kkoch.admin.api.controller.auction.response.AuctionArticlesResponse;
import com.kkoch.admin.domain.admin.Admin;
import com.kkoch.admin.domain.auction.Auction;
import com.kkoch.admin.domain.plant.Category;
import com.kkoch.admin.domain.plant.Plant;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@Transactional
class AdminQueryRepositoryTest extends IntegrationTestSupport {

    @DisplayName("[관리자 목록 조회]")
    @Test
    void getAdmins() {
        //given
        Admin.builder()
                .name("관리자")
                .loginId("admin")
                .loginPw("adminpw")
                .tel("010-1234-5678")
                .active(true)
                .position("00")
                .build();

        insertAdmin();

        Plant roseFuego = insertPlant(code, rose, fuego);
        Plant roseVictoria = insertPlant(code, rose, victoria);

        Admin admin = insertAdmin();
        Auction savedAuction1 = insertAuction(admin, LocalDateTime.of(2023, 9, 20, 5, 0));
        Auction savedAuction2 = insertAuction(admin, LocalDateTime.of(2023, 9, 20, 5, 0));

        insertAuctionArticle(roseFuego, savedAuction1, "서울", LocalDateTime.of(2023, 9, 20, 5, 0).minusDays(2));
        insertAuctionArticle(roseFuego, savedAuction2, "광주", LocalDateTime.of(2023, 9, 20, 5, 0).minusDays(10));
        insertAuctionArticle(roseFuego, savedAuction1, "광주", LocalDateTime.of(2023, 9, 20, 5, 0).minusDays(2));
        insertAuctionArticle(roseVictoria, savedAuction1, "서울", LocalDateTime.of(2023, 9, 20, 5, 0).minusDays(2));
        insertAuctionArticle(roseVictoria, savedAuction2, "광주", LocalDateTime.of(2023, 9, 20, 5, 0).minusDays(10));
        insertAuctionArticle(roseVictoria, savedAuction1, "광주", LocalDateTime.of(2023, 9, 20, 5, 0).minusDays(2));

        //when
        List<AuctionArticlesResponse> response = auctionArticleQueryRepository.getAuctionArticleList(savedAuction1.getId());

        //then
        assertThat(response).hasSize(4);
    }


}