package com.kkoch.admin.api.controller.auction;

import com.kkoch.admin.api.ApiResponse;
import com.kkoch.admin.api.controller.admin.LoginAdmin;
import com.kkoch.admin.api.controller.auction.request.AddAuctionRequest;
import com.kkoch.admin.api.controller.auction.request.SetAuctionRequest;
import com.kkoch.admin.api.controller.auction.response.AuctionTitleResponse;
import com.kkoch.admin.api.service.auction.AuctionService;
import com.kkoch.admin.api.service.auction.dto.AddAuctionDto;
import com.kkoch.admin.api.service.auction.dto.SetAuctionDto;
import com.kkoch.admin.domain.auction.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.MOVED_PERMANENTLY;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin-service/auctions")
@Slf4j
public class AuctionController {

    private final AuctionService auctionService;

    @PostMapping
    public ApiResponse<AuctionTitleResponse> addAuction(
            @Valid @RequestBody AddAuctionRequest request,
            @SessionAttribute(name = "loginAdmin") LoginAdmin loginAdmin) {

        timeValidation(request.getStartTime());

        AddAuctionDto dto = request.toAddAuctionDto();

        AuctionTitleResponse response = auctionService.addAuction(loginAdmin.getId(), dto);
        log.debug("[경매 일정 등록 응답] 경매방 제목 = {}", response.getTitle());
        return ApiResponse.ok(response);
    }

    @PatchMapping("/{auctionId}/{status}")
    public ApiResponse<AuctionTitleResponse> setAuctionStatus(
            @PathVariable Long auctionId,
            @PathVariable Status status,
            @SessionAttribute(name = "loginAdmin") LoginAdmin loginAdmin) {

        AuctionTitleResponse response = auctionService.setStatus(auctionId, status);

        log.debug("[경매 일정 상태 변경] 경매방 제목 = {}", response.getTitle());
        return ApiResponse.ok(response);
    }

    @PatchMapping("/{auctionId}")
    public ApiResponse<AuctionTitleResponse> setAuction(
            @PathVariable Long auctionId,
            @Valid @RequestBody SetAuctionRequest request,
            @SessionAttribute(name = "loginAdmin") LoginAdmin loginAdmin) {

        timeValidation(request.getStartTime());

        SetAuctionDto dto = request.toSetAuctionDto();

        AuctionTitleResponse response = auctionService.setAuction(auctionId, loginAdmin.getId(), dto);
        return ApiResponse.ok(response);
    }

    @DeleteMapping("/{auctionId}")
    public ApiResponse<Long> removeAuction(
            @PathVariable Long auctionId,
            @SessionAttribute(name = "loginAdmin") LoginAdmin loginAdmin) {

        Long removedAuctionId = auctionService.remove(auctionId);

        return ApiResponse.of(MOVED_PERMANENTLY, "경매 일정이 삭제되었습니다.", removedAuctionId);
    }

    private void timeValidation(LocalDateTime startTime) {
        if (!startTime.isAfter(LocalDateTime.now().plusHours(1))) {
            throw new IllegalArgumentException("경매 시간 입력 오류");
        }
    }
}
