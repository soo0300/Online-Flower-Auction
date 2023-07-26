package com.kkoch.admin.api.service.auction;

import com.kkoch.admin.api.controller.auction.response.AuctionResponse;
import com.kkoch.admin.api.service.auction.dto.AddAuctionDto;
import com.kkoch.admin.domain.auction.repository.AuctionQueryRepository;
import com.kkoch.admin.domain.auction.repository.AuctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AuctionQueryService {

    private final AuctionQueryRepository auctionQueryRepository;

    public List<AuctionResponse> getAuctionSchedule() {
        return auctionQueryRepository.findAllAuction();
    }
}
