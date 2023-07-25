package com.kkoch.user.api.controller.member.request;

import com.kkoch.user.api.service.member.dto.LoginMemberDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class LoginMemberRequest {

    @NotEmpty
    private String email;

    @NotEmpty
    private String loginPw;

    @Builder
    private LoginMemberRequest(String email, String loginPw) {
        this.email = email;
        this.loginPw = loginPw;
    }

    public LoginMemberDto toLoginMemberDto() {
        return LoginMemberDto.builder()
                .email(this.email)
                .loginPw(this.loginPw)
                .build();
    }
}
