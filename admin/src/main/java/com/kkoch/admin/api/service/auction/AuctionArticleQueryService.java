package com.kkoch.admin.api.service.auction;

import com.kkoch.admin.api.controller.auction.response.AuctionArticleForMemberResponse;
import com.kkoch.admin.api.controller.auction.response.AuctionArticlesResponse;
import com.kkoch.admin.domain.auction.repository.AuctionArticleQueryRepository;
import com.kkoch.admin.domain.auction.repository.dto.AuctionArticleSearchCond;
import com.kkoch.admin.domain.auction.repository.dto.AuctionArticleSearchForAdminCond;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AuctionArticleQueryService {

    private final AuctionArticleQueryRepository auctionArticleQueryRepository;

    public Page<AuctionArticleForMemberResponse> getAuctionArticleListForMember(AuctionArticleSearchCond cond, Pageable pageable) {
        List<AuctionArticleForMemberResponse> responses = auctionArticleQueryRepository.getAuctionArticleListForMember(cond, pageable);
        int totalCount = auctionArticleQueryRepository.getTotalCount(cond);
        return new PageImpl<>(responses, pageable, totalCount);
    }

    public List<AuctionArticlesResponse> getAuctionArticleListForAdmin(AuctionArticleSearchForAdminCond cond) {
        return auctionArticleQueryRepository.getAuctionArticleListForAdmin(cond);
    }
}
