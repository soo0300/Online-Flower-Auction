package com.kkoch.user.api.controller.alarm.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class AlarmResponse {

    private Long alarmId;
    private String content;
    private boolean open;
    private String createDate;

    public AlarmResponse(Long alarmId, String content, boolean open, LocalDateTime createDate) {
        this.alarmId = alarmId;
        this.content = content;
        this.open = open;
        this.createDate = createDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd hh:mm"));
    }
}
