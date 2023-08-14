package com.kkoch.admin.api.controller.notice.response;

import com.kkoch.admin.domain.admin.Admin;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
public class NoticeResponse {

    private Integer no;
    private Long noticeId;
    private String title;
    private String content;
//    private Boolean active;
//    private Admin admin;
    @Builder
    public NoticeResponse(Long noticeId, String title, String content) {
        this.noticeId = noticeId;
        this.title = title;
        this.content = content;
//        this.admin = admin;
//        this.active = active;
    }
}
