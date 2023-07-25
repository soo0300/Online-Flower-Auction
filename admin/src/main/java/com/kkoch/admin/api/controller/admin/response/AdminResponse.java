package com.kkoch.admin.api.controller.admin.response;

import lombok.Builder;
import lombok.Data;

@Data
public class AdminResponse {

    private String loginPw;
    private String name;
    private String tel;
    private String position;
    private boolean active;

    @Builder
    private AdminResponse(String loginPw, String name, String tel, String position, boolean active) {
        this.loginPw = loginPw;
        this.name = name;
        this.tel = tel;
        this.position = position;
        this.active = active;
    }
}
