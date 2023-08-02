package com.kkoch.admin.api.controller.notice;

import com.kkoch.admin.api.controller.admin.LoginAdmin;
import com.kkoch.admin.api.controller.notice.form.AddNoticeForm;
import com.kkoch.admin.api.controller.notice.form.SetNoticeForm;
import com.kkoch.admin.api.service.notice.NoticeService;
import com.kkoch.admin.api.service.notice.dto.AddNoticeDto;
import com.kkoch.admin.api.service.notice.dto.SetNoticeDto;
import com.kkoch.admin.login.Login;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@Slf4j
@RequestMapping("/notices")
public class NoticeController {

    private final NoticeService noticeService;

    @PostMapping("/add")
    public String addNotice(AddNoticeForm form, @Login LoginAdmin loginAdmin) {
        AddNoticeDto dto = form.toAddNoticeDto();
        Long noticeId = noticeService.addNotice(loginAdmin.getId(), dto);
        return "redirect:/";
    }

    @PostMapping("/{noticeId}/edit")
    public String setNotice(@PathVariable Long noticeId, SetNoticeForm form) {
        SetNoticeDto dto = form.toSetNoticeDto();
        Long id = noticeService.setNotice(noticeId, dto);
        return "redirect:/";
    }

    @PostMapping("/{noticeId}/remove")
    public String removeNotice(@PathVariable Long noticeId) {
        Long id = noticeService.removeNotice(noticeId);
        return "redirect:/";
    }
}
