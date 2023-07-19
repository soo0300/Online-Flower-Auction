package com.kkoch.user.api.controller.alarm;


import com.kkoch.user.api.controller.ApiResponse;
import com.kkoch.user.api.controller.alarm.response.AlarmResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user-service")
@Slf4j
@Api(tags = {"알림 기능"})
public class AlarmController {

    // 알림 조회
    @ApiOperation("알림 단건 조회")
    @GetMapping("/alarms/{id}")
    public ApiResponse<AlarmResponse> getAlarm(@PathVariable Long id){
        return ApiResponse.ok(null);
    }

    // 알림 목록 조회
    @ApiOperation("알림 목록 조회")
    @GetMapping("/alarms")
    public ApiResponse<List<AlarmResponse>> getAlarmList(){
        return ApiResponse.ok(null);
    }

    // 읽기 처리
    @ApiOperation("알림 확인")
    @PatchMapping("/alarms/{id}")
    public ApiResponse<?> setCheckAlarm(@PathVariable Long id){
        return ApiResponse.ok(null);
    }

    // 알림 삭제
    @ApiOperation("알림 삭제")
    @DeleteMapping("/alarms/{id}")
    public ApiResponse<?> removeAlaram(@PathVariable Long id){
        return ApiResponse.ok(null);
    }
}
