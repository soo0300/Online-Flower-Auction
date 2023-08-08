package com.kkoch.admin.api.controller.auction;

import com.kkoch.admin.api.ApiResponse;
import com.kkoch.admin.api.controller.admin.LoginAdmin;
import com.kkoch.admin.api.controller.auction.request.AddAuctionRequest;
import com.kkoch.admin.api.controller.auction.request.SetAuctionRequest;
import com.kkoch.admin.api.controller.auction.response.AuctionResponse;
import com.kkoch.admin.api.controller.auction.response.AuctionTitleResponse;
import com.kkoch.admin.api.service.auction.AuctionQueryService;
import com.kkoch.admin.api.service.auction.AuctionService;
import com.kkoch.admin.api.service.auction.dto.AddAuctionDto;
import com.kkoch.admin.api.service.auction.dto.SetAuctionDto;
import com.kkoch.admin.domain.auction.Status;
import com.kkoch.admin.login.Login;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.HttpStatus.MOVED_PERMANENTLY;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin-service/auctions")
@Slf4j
public class AuctionController {

    private final AuctionService auctionService;
    private final AuctionQueryService auctionQueryService;

    /**
     * 로그인을 한 관계자만 경매 일정을 등록할 수 있다.
     * @throws IllegalArgumentException
     * <pre>
     *     현재 시간 + 1시간 이전의 일정이 등록되는 경우<br/>
     *     구분코드가 범위(1 ~ 4)를 벗어나는 경우
     * </pre>
     *
     */
    @PostMapping
    public ApiResponse<AuctionTitleResponse> addAuction(
            @Valid @RequestBody AddAuctionRequest request,
            @Login LoginAdmin loginAdmin
    ) {

        timeValidation(request.getStartTime());

        AddAuctionDto dto = request.toAddAuctionDto();

        AuctionTitleResponse response = auctionService.addAuction(loginAdmin.getId(), dto);
        log.debug("[경매 일정 등록 응답] 경매방 제목 = {}", response.getTitle());
        return ApiResponse.ok(response);
    }

    @GetMapping("/api")
    public ApiResponse<AuctionTitleResponse> getAuctionListForMember() {
        AuctionTitleResponse openAuction = auctionQueryService.getOpenAuction();
        return ApiResponse.ok(openAuction);
    }

    @GetMapping
    public ApiResponse<List<AuctionResponse>> getAuctions() {
        List<AuctionResponse> auctionSchedule = auctionQueryService.getAuctionSchedule();
        return ApiResponse.ok(auctionSchedule);
    }

    @PatchMapping("/{auctionId}/{status}")
    public ApiResponse<AuctionTitleResponse> setAuctionStatus(
            @PathVariable Long auctionId,
            @PathVariable Status status
    ) {

        AuctionTitleResponse response = auctionService.setStatus(auctionId, status);

        log.debug("[경매 일정 상태 변경] 경매방 제목 = {}", response.getTitle());
        return ApiResponse.ok(response);
    }

    @PatchMapping("/{auctionId}")
    public ApiResponse<AuctionTitleResponse> setAuction(
            @PathVariable Long auctionId,
            @Valid @RequestBody SetAuctionRequest request
    ) {

        timeValidation(request.getStartTime());

        SetAuctionDto dto = request.toSetAuctionDto();

        AuctionTitleResponse response = auctionService.setAuction(auctionId, dto);
        return ApiResponse.ok(response);
    }

    @DeleteMapping("/{auctionId}")
    public ApiResponse<Long> removeAuction(@PathVariable Long auctionId) {

        Long removedAuctionId = auctionService.remove(auctionId);

        return ApiResponse.of(MOVED_PERMANENTLY, "경매 일정이 삭제되었습니다.", removedAuctionId);
    }

    private void timeValidation(LocalDateTime startTime) {
        if (!startTime.isAfter(LocalDateTime.now().plusHours(1))) {
            throw new IllegalArgumentException("경매 시간 입력 오류");
        }
    }
}
