package com.kkoch.user.api.controller.member.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EditLoginPwRequest {

    private String currentLoginPw;
    private String newLoginPw;
}
