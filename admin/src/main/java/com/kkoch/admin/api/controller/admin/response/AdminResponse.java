package com.kkoch.admin.api.controller.admin.response;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AdminResponse {

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
    public AdminResponse(String loginPw, String name, String tel, String position, boolean active) {
        this.loginPw = loginPw;
        this.name = name;
        this.tel = tel;
        this.position = position;
        this.active = active;
    }
}
