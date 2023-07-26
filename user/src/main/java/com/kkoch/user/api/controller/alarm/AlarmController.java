package com.kkoch.user.api.controller.alarm;

import com.kkoch.user.api.controller.ApiResponse;
import com.kkoch.user.api.controller.alarm.response.AlarmResponse;
import com.kkoch.user.api.service.alarm.AlarmQueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.MOVED_PERMANENTLY;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user-service/alarms")
@Slf4j
@Api(tags = {"알림 기능"})
public class AlarmController {

    private final AlarmQueryService alarmQueryService;

    @ApiOperation("알림 목록 조회")
    @GetMapping
    public ApiResponse<List<AlarmResponse>> getAlarms() {
        // TODO: 2023-07-26 임우택 JWT 복호화 과정 생략
        String email = null;
        List<AlarmResponse> responses = alarmQueryService.searchAlarms(email);
        return ApiResponse.ok(responses);
    }

    // 읽기 처리
    @ApiOperation("알림 확인")
    @PatchMapping("/{alarmId}")
    public ApiResponse<?> setCheckAlarm(@PathVariable Long alarmId) {
        return ApiResponse.ok(null);
    }

    // 알림 삭제
    @ApiOperation("알림 삭제")
    @DeleteMapping("/{alarmId}")
    public ApiResponse<?> removeAlarm(@PathVariable Long alarmId) {
        return ApiResponse.of(MOVED_PERMANENTLY, "알림 삭제", null);
    }
}
