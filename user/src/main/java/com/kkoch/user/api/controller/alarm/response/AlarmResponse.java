package com.kkoch.user.api.controller.alarm.response;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class AlarmResponse {


    Long id;
    String content;
    boolean open;
    String create_date;
}
