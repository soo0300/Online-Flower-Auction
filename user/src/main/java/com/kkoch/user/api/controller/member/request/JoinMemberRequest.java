package com.kkoch.user.api.controller.member.request;

import com.kkoch.user.api.service.member.dto.JoinMemberDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class JoinMemberRequest {

    @NotBlank
    private String email;
    @NotBlank
    private String loginPw;
    @NotBlank
    private String name;
    @NotBlank
    private String tel;
    @NotBlank
    private String businessNumber;
    private MultipartFile file;

    @Builder
    private JoinMemberRequest(String email, String loginPw, String name, String tel, String businessNumber, MultipartFile file) {
        this.email = email;
        this.loginPw = loginPw;
        this.name = name;
        this.tel = tel;
        this.businessNumber = businessNumber;
        this.file = file;
    }

    public JoinMemberDto toJoinMemberDto() {
        return JoinMemberDto.builder()
                .email(email)
                .loginPw(loginPw)
                .name(name)
                .tel(tel)
                .businessNumber(businessNumber)
                .file(file)
                .point(0)
                .active(true)
                .build();
    }
}
