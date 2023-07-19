package com.kkoch.user.api.controller.alarm;

import com.kkoch.user.api.controller.ApiResponse;
import com.kkoch.user.api.controller.alarm.response.AlarmResponse;
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
    // 알림 목록 조회
    @ApiOperation("알림 목록 조회")
    @GetMapping
    public ApiResponse<List<AlarmResponse>> getAlarms() {
        return ApiResponse.ok(null);
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
    public ApiResponse<?> removeAlaram(@PathVariable Long alarmId) {
        return ApiResponse.of(MOVED_PERMANENTLY, "알림 삭제", null);
    }
}
