package com.kkoch.user.api.controller.member.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class JoinMemberRequest {

    private String email;
    private String loginPw;
    private String name;
    private String tel;
    private String businessNumber;
    private MultipartFile file;
}
