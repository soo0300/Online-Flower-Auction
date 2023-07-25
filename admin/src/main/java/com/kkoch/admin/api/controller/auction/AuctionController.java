package com.kkoch.admin.api.controller.auction;

import com.kkoch.admin.api.ApiResponse;
import com.kkoch.admin.api.controller.admin.LoginAdmin;
import com.kkoch.admin.api.controller.auction.request.AddAuctionRequest;
import com.kkoch.admin.api.controller.auction.response.AuctionTitleResponse;
import com.kkoch.admin.api.service.auction.AuctionService;
import com.kkoch.admin.api.service.auction.dto.AddAuctionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin-service/auctions")
@Slf4j
public class AuctionController {

    private final AuctionService auctionService;

    @PostMapping
    public ApiResponse<AuctionTitleResponse> addAuction(@Valid @RequestBody AddAuctionRequest request,
                                                        @SessionAttribute(name = "loginAdmin") LoginAdmin loginAdmin) {
        LocalDateTime startTime = request.getStartTime();
        if (!startTime.isAfter(LocalDateTime.now().plusHours(1))) {
            return ApiResponse.of(BAD_REQUEST, "경매 시간 입력 오류", null);
        }

        AddAuctionDto dto = request.toAddAuctionDto();

        AuctionTitleResponse response = auctionService.addAuction(loginAdmin.getId(), dto);
        log.debug("[경매 일정 등록 응답] 경매방 제목 = {}", response.getTitle());
        return ApiResponse.ok(response);
    }

    @PatchMapping
    public ApiResponse<AuctionTitleResponse> setAuctionStatus() {
        return null;
    }
}
