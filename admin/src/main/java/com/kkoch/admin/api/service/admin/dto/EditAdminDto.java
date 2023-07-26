package com.kkoch.admin.api.service.admin.dto;

import com.kkoch.admin.domain.admin.Admin;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EditAdminDto {


    private String loginPw;
    private String tel;

    @Builder
    private EditAdminDto(String loginPw, String tel) {
        this.loginPw = loginPw;
        this.tel = tel;
    }

    public Admin toEntity() {
        return Admin.builder()
                .loginPw(this.loginPw)
                .tel(this.tel)
                .build();
    }


}
