package com.kkoch.admin.api.service.auction;

import com.kkoch.admin.api.controller.auction.response.AuctionTitleResponse;
import com.kkoch.admin.api.service.auction.dto.AddAuctionDto;
import com.kkoch.admin.api.service.auction.dto.SetAuctionDto;
import com.kkoch.admin.api.service.auction.dto.SetAuctionStatusDto;
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

    public AuctionTitleResponse setAuction(Long adminId, SetAuctionDto dto) {
        return null;
    }

    public AuctionTitleResponse setStatus(SetAuctionStatusDto dto) {
        Long auctionId = dto.getAuctionId();
        Status status = dto.getStatus();

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
