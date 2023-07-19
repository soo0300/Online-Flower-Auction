package com.kkoch.user.api.controller.alarm.response;

import lombok.Data;

@Data
public class AlarmResponse {
    private Long id;
    private String content;
    private boolean open;
    private String createDate;
}
