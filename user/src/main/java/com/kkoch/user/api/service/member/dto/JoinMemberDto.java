package com.kkoch.user.api.service.member.dto;

import com.kkoch.user.domain.member.Member;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class JoinMemberDto {

    private String email;
    private String loginPw;
    private String name;
    private String tel;
    private String businessNumber;
    private MultipartFile file;
    private int point;
    private boolean active;

    @Builder
    private JoinMemberDto(String email, String loginPw, String name, String tel, String businessNumber, MultipartFile file, int point, boolean active) {
        this.email = email;
        this.loginPw = loginPw;
        this.name = name;
        this.tel = tel;
        this.businessNumber = businessNumber;
        this.file = file;
        this.point = point;
        this.active = active;
    }

    public Member toEntity() {
        return Member.builder()
                .email(this.email)
                .loginPw(this.loginPw)
                .name(this.name)
                .tel(this.tel)
                .businessNumber(this.businessNumber)
                .point(0)
                .active(true)
                .build();
    }
}
