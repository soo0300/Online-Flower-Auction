package com.kkoch.admin.api.controller.auction;

import com.kkoch.admin.api.ApiResponse;
import com.kkoch.admin.api.controller.auction.request.AddAuctionArticleRequest;
import com.kkoch.admin.api.controller.auction.response.AuctionArticleForMemberResponse;
import com.kkoch.admin.api.service.auction.AuctionArticleQueryService;
import com.kkoch.admin.api.service.auction.AuctionArticleService;
import com.kkoch.admin.api.service.auction.dto.AddAuctionArticleDto;
import com.kkoch.admin.domain.auction.repository.dto.AuctionArticleSearchCond;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin-service/auction-articles")
@Slf4j
public class AuctionArticleController {

    private final AuctionArticleService auctionArticleService;
    private final AuctionArticleQueryService auctionArticleQueryService;

    @PostMapping
    public ApiResponse<Long> addAuctionArticle(
            @Valid @RequestBody AddAuctionArticleRequest request
    ) {
        AddAuctionArticleDto dto = request.toAddAuctionArticleDto();
        Long savedAuctionArticleId = auctionArticleService.addAuctionArticle(request.getPlantId(), request.getAuctionId(), dto);
        log.debug("[경매품 등록 응답] 경매품 상장번호 = {}", savedAuctionArticleId);
        return ApiResponse.ok(savedAuctionArticleId);
    }

    @GetMapping("/api")
    public ApiResponse<Page<AuctionArticleForMemberResponse>> getAuctionArticlesForMember(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDateTime,
            @RequestParam(defaultValue = "절화") String code,
            @RequestParam @Nullable String type,
            @RequestParam @Nullable String name,
            @RequestParam @Nullable String region,
            @RequestParam(defaultValue = "0") Integer page
    ) {
        AuctionArticleSearchCond cond = AuctionArticleSearchCond.of(endDateTime, code, type, name, region);
        PageRequest pageRequest = PageRequest.of(page, 15);

        Page<AuctionArticleForMemberResponse> responses = auctionArticleQueryService.getAuctionArticleListForMember(cond, pageRequest);
        return ApiResponse.ok(responses);
    }


}
