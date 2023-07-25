package com.kkoch.admin.api.service.auction;


import com.kkoch.admin.IntegrationTestSupport;
import com.kkoch.admin.api.controller.auction.response.AuctionTitleResponse;
import com.kkoch.admin.api.service.auction.dto.AddAuctionDto;
import com.kkoch.admin.api.service.auction.dto.SetAuctionDto;
import com.kkoch.admin.api.service.auction.dto.SetAuctionStatusDto;
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
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.kkoch.admin.domain.auction.Status.*;


@Transactional
class AuctionServiceTest extends IntegrationTestSupport {

    @Autowired
    private AuctionService auctionService;
    @Autowired
    private AuctionRepository auctionRepository;
    @Autowired
    private AdminRepository adminRepository;

    @DisplayName("[경매 일정 변경] 존재하지 않는 경매일정을 수정할 경우 에러가 발생한다.")
    @Test
    void setAuctionIdError() {
        //given
        Admin admin = insertAdmin();
        SetAuctionDto dto = SetAuctionDto.builder()
                .startTime(LocalDateTime.of(2023, 9, 20, 5, 0))
                .code(2)
                .build();
        //when //then
        Assertions.assertThatThrownBy(() -> auctionService.setAuction(5L, admin.getId(), dto))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("존재하지 않는 경매 일정");
    }

    @DisplayName("[경매 일정 변경] 구분코드 에러를 검증한다.")
    @Test
    void setAuctionTimeError() {
        //given
        Admin admin = insertAdmin();
        Auction auction = insertAuction(admin);
        SetAuctionDto dto = SetAuctionDto.builder()
                .startTime(LocalDateTime.of(2023, 9, 20, 5, 0))
                .code(-2)
                .build();
        //when //then
        Assertions.assertThatThrownBy(() -> auctionService.setAuction(auction.getId(), admin.getId(), dto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("구분코드 에러");
    }

    @DisplayName("[경매 일정 변경]")
    @Test
    void setAuction() {
        //given
        Admin admin = insertAdmin();
        Auction auction = insertAuction(admin);
        SetAuctionDto dto = SetAuctionDto.builder()
                .startTime(LocalDateTime.of(2023, 9, 20, 5, 0))
                .code(4)
                .build();

        //when
        AuctionTitleResponse response = auctionService.setAuction(auction.getId(), admin.getId(), dto);

        //then
        Assertions.assertThat(response.getTitle()).isEqualTo("23. 9. 20. 오전 5:00 춘화 준비 중");
    }

    @DisplayName("[경매 상태 수정] 경매상태를 수정할 때 잘못된 pk의 경매일정을 수정하면 에러가 발생한다.")
    @Test
    void setAuctionPKError() {
        //given
        SetAuctionStatusDto dto = SetAuctionStatusDto.builder()
                .auctionId(2L)
                .status(OPEN)
                .build();

        //when //then
        Assertions.assertThatThrownBy(() -> auctionService.setStatus(dto))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("잘못된 옥션 PK");

    }

    @DisplayName("[경매 상태 수정]")
    @Test
    void setAuctionStatus() {
        //given
        Admin admin = insertAdmin();

        Auction savedAuction = insertAuction(admin);
        SetAuctionStatusDto dto = SetAuctionStatusDto.builder()
                .auctionId(savedAuction.getId())
                .status(CLOSE)
                .build();

        //when //then
        AuctionTitleResponse response = auctionService.setStatus(dto);
        Assertions.assertThat(response.getTitle()).isEqualTo("23. 9. 20. 오전 5:00 절화 마감");

    }


    @DisplayName("[경매일정 등록]시 구분코드 에러를 검증한다.")
    @Test
    void addAuctionCodeError() {
        //given
        Admin admin = insertAdmin();
        AddAuctionDto dto = AddAuctionDto.builder()
                .startTime(LocalDateTime.of(2023, 9, 20, 5, 0))
                .code(-1)
                .build();

        //when

        //then
        Assertions.assertThatThrownBy(() -> auctionService.addAuction(admin.getId(), dto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("구분코드 에러");
    }

    @DisplayName("[경매일정 등록]")
    @Test
    void addAuction() {
        //given
        Admin admin = insertAdmin();
        AddAuctionDto dto = AddAuctionDto.builder()
                .startTime(LocalDateTime.of(2023, 9, 20, 5, 0))
                .code(1)
                .build();

        //when
        AuctionTitleResponse response = auctionService.addAuction(admin.getId(), dto);

        //then
        Optional<Auction> findAuction = auctionRepository.findById(response.getAuctionId());
        Assertions.assertThat(findAuction).isPresent();
    }


    private Auction insertAuction(Admin admin) {
        Auction auction = Auction.builder()
                .code(1)
                .startTime(LocalDateTime.of(2023, 9, 20, 5, 0))
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