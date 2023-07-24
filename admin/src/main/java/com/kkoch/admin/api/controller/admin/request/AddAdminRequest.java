package com.kkoch.admin.api.controller.admin.request;

import com.kkoch.admin.api.service.admin.dto.AddAdminDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class AddAdminRequest {

    @NotBlank
    private String loginId;
    @NotBlank
    private String loginPw;
    @NotBlank
    private String name;
    @NotBlank
    private String tel;
    @NotBlank
    private String position;
    @NotBlank
    private boolean active;

    @Builder
    public AddAdminRequest(String loginId, String loginPw, String name, String tel, String position, boolean active) {
        this.loginId = loginId;
        this.loginPw = loginPw;
        this.name = name;
        this.tel = tel;
        this.position = position;
        this.active = active;
    }

    public AddAdminDto addAdminDto(){
        AddAdminDto dto = AddAdminDto.builder()
                .loginId(this.loginId)
                .loginPw(this.loginPw)
                .name(this.name)
                .tel(tel)
                .position(position)
                .active(active)
                .build();
        return dto;
    }
}
