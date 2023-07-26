package com.kkoch.admin.api.controller.admin.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdminResponse {

    private String loginId;
    private String loginPw;
    private String name;
    private String tel;
    private String position;
    private boolean active;

    @Builder
    private AdminResponse(String loginId, String loginPw, String name, String tel, String position, boolean active) {
        this.loginId = loginId;
        this.loginPw = loginPw;
        this.name = name;
        this.tel = tel;
        this.position = position;
        this.active = active;
    }
}
