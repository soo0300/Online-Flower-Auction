package com.kkoch.admin.api.service.auction;

import com.kkoch.admin.IntegrationTestSupport;
import com.kkoch.admin.api.controller.auction.response.AuctionArticleForMemberResponse;
import com.kkoch.admin.domain.Grade;
import com.kkoch.admin.domain.admin.Admin;
import com.kkoch.admin.domain.admin.repository.AdminRepository;
import com.kkoch.admin.domain.auction.Auction;
import com.kkoch.admin.domain.auction.AuctionArticle;
import com.kkoch.admin.domain.auction.repository.AuctionArticleRepository;
import com.kkoch.admin.domain.auction.repository.AuctionRepository;
import com.kkoch.admin.domain.auction.repository.dto.AuctionArticleSearchCond;
import com.kkoch.admin.domain.plant.Category;
import com.kkoch.admin.domain.plant.Plant;
import com.kkoch.admin.domain.plant.repository.CategoryRepository;
import com.kkoch.admin.domain.plant.repository.PlantRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.kkoch.admin.domain.auction.Status.READY;
import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class AuctionArticleQueryServiceTest extends IntegrationTestSupport {

    @Autowired
    private AuctionArticleQueryService auctionArticleQueryService;
    @Autowired
    private AuctionArticleRepository auctionArticleRepository;
    @Autowired
    private AuctionRepository auctionRepository;
    @Autowired
    private PlantRepository plantRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private AdminRepository adminRepository;

    @DisplayName("[실시간 거래실적 조회]")
    @Test
    void getAuctionArticleList() {
        //given
        Category code = insertCategory("절화");
        Category rose = insertCategory("장미");
        Category fuego = insertCategory("푸에고");
        Category victoria = insertCategory("빅토리아");

        Plant roseFuego = insertPlant(code, rose, fuego);
        Plant roseVictoria = insertPlant(code, rose, victoria);

        Admin admin = insertAdmin();
        Auction savedAuction = insertAuction(admin, LocalDateTime.of(2023, 9, 20, 5, 0));

        insertAuctionArticle(roseFuego, savedAuction, "서울", LocalDateTime.of(2023, 9, 20, 5, 0).minusDays(2));
        insertAuctionArticle(roseFuego, savedAuction, "광주", LocalDateTime.of(2023, 9, 20, 5, 0).minusDays(10));
        insertAuctionArticle(roseFuego, savedAuction, "광주", LocalDateTime.of(2023, 9, 20, 5, 0).minusDays(2));
        insertAuctionArticle(roseVictoria, savedAuction, "서울", LocalDateTime.of(2023, 9, 20, 5, 0).minusDays(2));
        insertAuctionArticle(roseVictoria, savedAuction, "광주", LocalDateTime.of(2023, 9, 20, 5, 0).minusDays(10));
        insertAuctionArticle(roseVictoria, savedAuction, "광주", LocalDateTime.of(2023, 9, 20, 5, 0).minusDays(2));

        AuctionArticleSearchCond cond = AuctionArticleSearchCond.builder()
                .code("절화")
                .type("장미")
                .name("푸에고")
                .endDateTime(LocalDateTime.of(2023, 9, 20, 5, 0).toLocalDate())
                .region("광주")
                .build();
        PageRequest pageRequest = PageRequest.of(0, 20);

        //when
        Page<AuctionArticleForMemberResponse> result = auctionArticleQueryService.getAuctionArticleListForMember(cond, pageRequest);

        //then
        List<AuctionArticleForMemberResponse> responses = result.getContent();
        assertThat(responses).hasSize(1);
    }

    private Plant insertPlant(Category code, Category type, Category name) {
        return plantRepository.save(Plant.builder()
                .code(code)
                .type(type)
                .name(name)
                .build());
    }

    private Category insertCategory(String name) {
        Category category = Category.builder()
                .name(name)
                .build();
        return categoryRepository.save(category);
    }

    private AuctionArticle insertAuctionArticle(Plant plant, Auction auction, String region, LocalDateTime bidTime) {
        AuctionArticle auctionArticle = AuctionArticle.builder()
                .plant(plant)
                .auction(auction)
                .auctionNumber("00001")
                .grade(Grade.NONE)
                .count(10)
                .region(region)
                .shipper("꽃파라")
                .startPrice(20000)
                .bidPrice(10000)
                .bidTime(bidTime)
                .build();
        return auctionArticleRepository.save(auctionArticle);
    }

    private Auction insertAuction(Admin admin, LocalDateTime startTime) {
        Auction auction = Auction.builder()
                .code(1)
                .startTime(startTime)
                .active(true)
                .status(READY)
                .admin(admin)
                .build();
        return auctionRepository.save(auction);
    }

    private Admin insertAdmin() {
        Admin admin = Admin.builder()
                .loginId("admin")
                .loginPw("admin123!")
                .name("관리자")
                .position("10")
                .tel("010-0000-0000")
                .active(true)
                .build();
        return adminRepository.save(admin);
    }
}