package com.kkoch.user.domain.member;

import com.kkoch.user.domain.TimeBaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Member extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, updatable = false, length = 20)
    private String name;

    @Column(nullable = false, unique = true, updatable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 60)
    private String loginPw;

    @Column(nullable = false, unique = true, length = 13)
    private String tel;

    @Column(nullable = false, unique = true, length = 12)
    private String businessNumber;

    @Column(nullable = false)
    private int point;

    @Column(nullable = false)
    private boolean active;

    @Builder
    private Member(String email, String loginPw, String tel, String businessNumber, int point, boolean active) {
        this.email = email;
        this.loginPw = loginPw;
        this.tel = tel;
        this.businessNumber = businessNumber;
        this.point = point;
        this.active = active;
    }
}
