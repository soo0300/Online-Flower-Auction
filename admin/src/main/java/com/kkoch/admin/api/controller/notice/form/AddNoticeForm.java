package com.kkoch.admin.api.controller.notice.form;

import com.kkoch.admin.api.service.notice.dto.AddNoticeDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class AddNoticeForm {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    public AddNoticeDto toAddNoticeDto() {
        return AddNoticeDto.builder()
                .title(this.title)
                .content(this.content)
                .build();
    }
}
