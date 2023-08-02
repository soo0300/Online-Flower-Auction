package com.kkoch.admin.api.controller.notice;

import com.kkoch.admin.api.ApiResponse;
import com.kkoch.admin.api.controller.notice.response.NoticeResponse;
import com.kkoch.admin.domain.notice.repository.NoticeQueryRepository;
import com.kkoch.admin.domain.notice.repository.dto.NoticeSearchCond;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/admin-service/notices")
public class NoticeApiController {

    private final NoticeQueryRepository noticeQueryRepository;

    @GetMapping
    public ApiResponse<List<NoticeResponse>> getNotices(
            @RequestParam Integer type,
            @RequestParam String keyword,
            @RequestParam Integer pageNum
    ) {
        NoticeSearchCond cond = getCond(type, keyword);

        PageRequest request = PageRequest.of(pageNum - 1, 10);

        List<NoticeResponse> content = noticeQueryRepository.getNoticeByCondition(cond, request);

        return ApiResponse.ok(content);
    }

    private NoticeSearchCond getCond(int type, String keyword) {
        if (type == 1) {
            return NoticeSearchCond.builder()
                    .title(keyword)
                    .build();
        }
        return NoticeSearchCond.builder()
                .content(keyword)
                .build();
    }
}
