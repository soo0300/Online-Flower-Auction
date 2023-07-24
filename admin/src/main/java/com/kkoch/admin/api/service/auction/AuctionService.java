package com.kkoch.admin.api.service.auction;

import com.kkoch.admin.api.controller.auction.response.AuctionTitleResponse;
import com.kkoch.admin.api.service.auction.dto.AddAuctionDto;
import com.kkoch.admin.domain.admin.Admin;
import com.kkoch.admin.domain.auction.Auction;
import com.kkoch.admin.domain.auction.repository.AuctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class AuctionService {

    private final AuctionRepository auctionRepository;

    public AuctionTitleResponse addAuction(Long adminId, AddAuctionDto dto) {
        int code = dto.getCode();

        if (code < 0 || code > 4) {
            throw new IllegalArgumentException("구분코드 에러");
        }

        Admin admin = Admin.toEntity(adminId);

        Auction auction = Auction.toEntity(dto.getCode(), dto.getStartTime(), admin);

        Auction savedAuction = auctionRepository.save(auction);

        return AuctionTitleResponse.builder()
                .auctionId(savedAuction.getId())
                .title(savedAuction.getTitle())
                .build();
    }


}
