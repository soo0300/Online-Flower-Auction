package com.kkoch.admin.api.service.auction;

import com.kkoch.admin.api.controller.auction.response.AuctionForMemberResponse;
import com.kkoch.admin.api.controller.auction.response.AuctionResponse;
import com.kkoch.admin.domain.auction.Auction;
import com.kkoch.admin.domain.auction.repository.AuctionQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AuctionQueryService {

    private final AuctionQueryRepository auctionQueryRepository;

    public List<AuctionResponse> getAuctionSchedule() {
        return auctionQueryRepository.findAllAuction();
    }

    public List<AuctionForMemberResponse> getAuctionForMember() {
        List<Auction> auctions = auctionQueryRepository.findAuctionForMember();
        return auctions.stream()
                .map(AuctionForMemberResponse::of)
                .collect(Collectors.toList());
    }
}
