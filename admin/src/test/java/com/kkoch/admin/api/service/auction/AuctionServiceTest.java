package com.kkoch.admin.api.service.auction;


import com.kkoch.admin.IntegrationTestSupport;
import com.kkoch.admin.api.service.auction.dto.AddAuctionDto;
import com.kkoch.admin.domain.admin.Admin;
import com.kkoch.admin.domain.admin.repository.AdminRepository;
import com.kkoch.admin.domain.auction.Auction;
import com.kkoch.admin.domain.auction.repository.AuctionRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;


@Transactional
class AuctionServiceTest extends IntegrationTestSupport {

    @Autowired
    private AuctionService auctionService;
    @Autowired
    private AuctionRepository auctionRepository;
    @Autowired
    private AdminRepository adminRepository;

    @DisplayName("경매일정 등록")
    @Test
    void addAuction() {
        //given
        Admin admin = insertAdmin();
        AddAuctionDto dto = AddAuctionDto.builder()
                .startTime(LocalDateTime.of(2023, 9, 20, 5, 0))
                .code(1)
                .build();

        //when
        Long auctionId = auctionService.addAuction(admin.getId(), dto);

        //then
        Optional<Auction> findAuction = auctionRepository.findById(auctionId);
        Assertions.assertThat(findAuction).isPresent();
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