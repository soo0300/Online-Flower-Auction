package com.kkoch.user.api.service.member.dto;

import com.kkoch.user.domain.member.Member;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
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
    private JoinMemberDto(@NonNull String email, @NonNull String loginPw, @NonNull String name, @NonNull String tel, @NonNull String businessNumber, MultipartFile file, int point, @NonNull boolean active) {
        this.email = email;
        this.loginPw = loginPw;
        this.name = name;
        this.tel = tel;
        this.businessNumber = businessNumber;
        this.point = point;
        this.active = active;
    }

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .loginPw(loginPw)
                .name(name)
                .tel(tel)
                .businessNumber(businessNumber)
                .point(0)
                .active(true)
                .build();
    }
}
