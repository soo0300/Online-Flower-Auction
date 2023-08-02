package com.kkoch.admin.api.controller.notice.form;

import com.kkoch.admin.api.service.notice.dto.AddNoticeDto;
import com.kkoch.admin.api.service.notice.dto.SetNoticeDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class SetNoticeForm {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    public SetNoticeDto toSetNoticeDto() {
        return SetNoticeDto.builder()
                .title(this.title)
                .content(this.content)
                .build();
    }
}
