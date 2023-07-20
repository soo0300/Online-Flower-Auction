package com.kkoch.user.domain.reservation;

import com.kkoch.user.domain.TimeBaseEntity;
import com.kkoch.user.domain.member.Member;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "reservation")
@Getter

public class Reservation extends TimeBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="reservation_id")
    private Long id;
    private Long memberId;
    private int count;
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private long plantId;

}