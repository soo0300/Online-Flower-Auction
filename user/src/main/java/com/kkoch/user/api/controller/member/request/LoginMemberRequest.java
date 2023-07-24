package com.kkoch.user.api.controller.member.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginMemberRequest {

    private String email;

    private String loginPw;
}
