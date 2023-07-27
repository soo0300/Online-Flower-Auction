package com.kkoch.admin.api.controller.auction;

import com.kkoch.admin.api.ApiResponse;
import com.kkoch.admin.api.controller.auction.request.AddAuctionArticleRequest;
import com.kkoch.admin.api.service.auction.AuctionArticleService;
import com.kkoch.admin.api.service.auction.dto.AddAuctionArticleDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin-service/auction-articles")
@Slf4j
public class AuctionArticleController {

    private final AuctionArticleService auctionArticleService;

    @PostMapping
    public ApiResponse<Long> addAuctionArticle(
            @Valid @RequestBody AddAuctionArticleRequest request
    ) {
        AddAuctionArticleDto dto = request.toAddAuctionArticleDto();
        Long savedAuctionArticleId = auctionArticleService.addAuctionArticle(request.getPlantId(), request.getAuctionId(), dto);
        log.debug("[경매품 등록 응답] 경매품 상장번호 = {}", savedAuctionArticleId);
        return ApiResponse.ok(savedAuctionArticleId);
    }
}
