package com.kkoch.admin.api.controller.auction;

import com.kkoch.admin.api.ApiResponse;
import com.kkoch.admin.api.controller.admin.LoginAdmin;
import com.kkoch.admin.api.controller.auction.request.AddAuctionRequest;
import com.kkoch.admin.api.controller.auction.request.SetAuctionRequest;
import com.kkoch.admin.api.controller.auction.response.AuctionTitleResponse;
import com.kkoch.admin.api.service.auction.AuctionService;
import com.kkoch.admin.api.service.auction.dto.AddAuctionDto;
import com.kkoch.admin.api.service.auction.dto.SetAuctionDto;
import com.kkoch.admin.api.service.auction.dto.SetAuctionStatusDto;
import com.kkoch.admin.domain.auction.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

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
        validationTime(request.getStartTime());

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
        SetAuctionStatusDto dto = toSetAuctionStatusDto(auctionId, status);

        AuctionTitleResponse response = auctionService.setStatus(dto);

        log.debug("[경매 일정 상태 변경] 경매방 제목 = {}", response.getTitle());
        return ApiResponse.ok(response);
    }

    @PatchMapping("/{auctionId}")
    public ApiResponse<AuctionTitleResponse> setAuction(
            @PathVariable Long auctionId,
            @Valid @RequestBody SetAuctionRequest request,
            @SessionAttribute(name = "loginAdmin") LoginAdmin loginAdmin) {

        LocalDateTime startTime = validationTime(request.getStartTime());

        SetAuctionDto dto = SetAuctionDto.builder()
                .auctionId(auctionId)
                .code(request.getCode())
                .startTime(startTime)
                .build();

        AuctionTitleResponse response = auctionService.setAuction(loginAdmin.getId(), dto);
        return ApiResponse.ok(response);
    }

    private static LocalDateTime validationTime(LocalDateTime request) {
        LocalDateTime startTime = request;
        if (!startTime.isAfter(LocalDateTime.now().plusHours(1))) {
            throw new IllegalArgumentException("경매 시간 입력 오류");
        }
        return startTime;
    }

    private static SetAuctionStatusDto toSetAuctionStatusDto(Long auctionId, Status status) {
        return SetAuctionStatusDto.builder()
                .status(status)
                .auctionId(auctionId)
                .build();
    }
}
