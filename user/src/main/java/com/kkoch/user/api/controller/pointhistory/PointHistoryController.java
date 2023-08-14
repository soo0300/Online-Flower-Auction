package com.kkoch.user.api.controller.pointhistory;

import com.kkoch.user.api.controller.ApiResponse;
import com.kkoch.user.api.controller.pointhistory.request.AddPointHistoryRequest;
import com.kkoch.user.api.controller.pointhistory.response.AddPointHistoryResponse;
import com.kkoch.user.api.controller.pointhistory.response.PointHistoryResponse;
import com.kkoch.user.api.service.pointhistory.PointHistoryQueryService;
import com.kkoch.user.api.service.pointhistory.PointHistoryService;
import com.kkoch.user.api.service.pointhistory.dto.AddPointHistoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/{memberKey}/points")
@Slf4j
public class PointHistoryController {

    private final PointHistoryService pointHistoryService;
    private final PointHistoryQueryService pointHistoryQueryService;

    @PostMapping("/charge")
    public ApiResponse<AddPointHistoryResponse> chargePointHistory(
        @PathVariable String memberKey,
        @RequestBody AddPointHistoryRequest request
    ) {
        AddPointHistoryDto dto = request.toAddPointHistoryDto(1);
        AddPointHistoryResponse response = pointHistoryService.addPointHistory(memberKey, dto);
        return ApiResponse.ok(response);
    }

    @PostMapping("/use")
    public ApiResponse<AddPointHistoryResponse> usePointHistory(
        @PathVariable String memberKey,
        @RequestBody AddPointHistoryRequest request
    ) {
        AddPointHistoryDto dto = request.toAddPointHistoryDto(2);
        AddPointHistoryResponse response = pointHistoryService.addPointHistory(memberKey, dto);
        return ApiResponse.ok(response);
    }

    @GetMapping
    public ApiResponse<Page<PointHistoryResponse>> getMyPointHistory(
        @PathVariable String memberKey,
        @RequestParam(defaultValue = "0") Integer pageNum
    ) {
        PageRequest request = PageRequest.of(pageNum, 15);
        Page<PointHistoryResponse> responses = pointHistoryQueryService.getMyPointHistories(memberKey, request);
        return ApiResponse.ok(responses);
    }
}
