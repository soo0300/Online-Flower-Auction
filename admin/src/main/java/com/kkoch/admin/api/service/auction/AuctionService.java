package com.kkoch.admin.api.service.auction;

import com.kkoch.admin.api.controller.auction.response.AuctionTitleResponse;
import com.kkoch.admin.api.service.auction.dto.AddAuctionDto;
import com.kkoch.admin.api.service.auction.dto.SetAuctionDto;
import com.kkoch.admin.domain.admin.Admin;
import com.kkoch.admin.domain.auction.Auction;
import com.kkoch.admin.domain.auction.Status;
import com.kkoch.admin.domain.auction.repository.AuctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class AuctionService {

    private final AuctionRepository auctionRepository;

    public AuctionTitleResponse setAuction(Long auctionId, Long adminId, SetAuctionDto dto) {
        int code = dto.getCode();

        if (betweenOneToFour(code)) {
            throw new IllegalArgumentException("구분코드 에러");
        }

        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 경매 일정"));


        Admin admin = Admin.toEntity(adminId);

        auction.changeAuction(dto.getCode(), dto.getStartTime(), admin);

        return AuctionTitleResponse.of(auction);
    }

    public AuctionTitleResponse setStatus(Long auctionId, Status status) {

        Optional<Auction> findAuction = auctionRepository.findById(auctionId);
        if (findAuction.isEmpty()) {
            throw new NoSuchElementException("잘못된 옥션 PK");
        }

        Auction auction = findAuction.get();

        auction.changeStatus(status);

        return AuctionTitleResponse.of(auction);
    }

    public AuctionTitleResponse addAuction(Long adminId, AddAuctionDto dto) {
        int code = dto.getCode();

        if (betweenOneToFour(code)) {
            throw new IllegalArgumentException("구분코드 에러");
        }

        Admin admin = Admin.toEntity(adminId);

        Auction auction = Auction.toEntity(dto.getCode(), dto.getStartTime(), admin);

        Auction savedAuction = auctionRepository.save(auction);

        return AuctionTitleResponse.of(savedAuction);
    }

    private boolean betweenOneToFour(int code) {
        return code < 0 || code > 4;
    }


}