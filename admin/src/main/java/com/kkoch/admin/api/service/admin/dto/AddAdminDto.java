package com.kkoch.admin.api.service.admin.dto;


import com.kkoch.admin.domain.admin.Admin;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddAdminDto {

    private String loginId;

    private String loginPw;

    private String name;

    private String tel;

    private String position;

    private boolean active;

    @Builder
    public AddAdminDto(String loginId, String loginPw, String name, String tel, String position, boolean active) {
        this.loginId = loginId;
        this.loginPw = loginPw;
        this.name = name;
        this.tel = tel;
        this.position = position;
        this.active = active;
    }

    public Admin toEntity() {
        return Admin.builder()
                .loginId(this.loginId)
                .loginPw(this.loginPw)
                .name(this.name)
                .tel(this.tel)
                .position(this.position)
                .active(true)
                .build();
    }
}
