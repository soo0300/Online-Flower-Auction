package com.kkoch.user.domain.member;

import com.kkoch.user.domain.TimeBaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Member extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, updatable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 100)
    private String encryptedPwd;

    @Column(nullable = false, updatable = false, length = 20)
    private String name;

    @Column(unique = true, nullable = false, length = 13)
    private String tel;

    @Column(unique = true, nullable = false, updatable = false, length = 12)
    private String businessNumber;

    @Column(nullable = false)
    private int point;

    @Column(nullable = false)
    private boolean active;

    @Column(unique = true, nullable = false, length = 100)
    private String memberKey;

    @Builder
    public Member(String email, String encryptedPwd, String name, String tel, String businessNumber, int point, boolean active, String memberKey) {
        this.email = email;
        this.encryptedPwd = encryptedPwd;
        this.name = name;
        this.tel = tel;
        this.businessNumber = businessNumber;
        this.point = point;
        this.active = active;
        this.memberKey = memberKey;
    }
}