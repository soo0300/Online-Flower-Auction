package com.kkoch.user.api.controller.pointhistory;

import com.kkoch.user.api.controller.ApiResponse;
import com.kkoch.user.api.controller.pointhistory.response.PointHistoryResponse;
import com.kkoch.user.api.service.pointhistory.PointHistoryQueryService;
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

    private final PointHistoryQueryService pointHistoryQueryService;

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
