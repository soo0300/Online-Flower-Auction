package com.kkoch.user.domain.member;

import com.kkoch.user.domain.TimeBaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@Getter
@Entity
public class Member extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String email;

    private String loginPw;

    private String tel;

    private String businessNumber;

    private int point;

    private boolean active;

    @Builder
    public Member(Long id, String email, String loginPw, String tel, String businessNumber, int point, boolean active) {
        this.id = id;
        this.email = email;
        this.loginPw = loginPw;
        this.tel = tel;
        this.businessNumber = businessNumber;
        this.point = point;
        this.active = active;
    }
}
