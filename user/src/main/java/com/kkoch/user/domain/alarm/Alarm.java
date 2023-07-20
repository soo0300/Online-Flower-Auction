package com.kkoch.user.domain.alarm;

import com.kkoch.user.domain.TimeBaseEntity;
import com.kkoch.user.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@Getter
@Entity
public class Alarm extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alarm")
    private Long id;

    private String content;

    private boolean open;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Alarm(Long id, String content, boolean open, Member member) {
        this.id = id;
        this.content = content;
        this.open = open;
        this.member = member;
    }
}
