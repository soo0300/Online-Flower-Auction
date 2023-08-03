package com.kkoch.user.domain.reservation;

import com.kkoch.user.domain.TimeBaseEntity;
import com.kkoch.user.domain.member.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    @Column(nullable = false)
    private int count;

    @Column(nullable = false)
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", updatable = false, nullable = false)
    private Member member;

    @Column(nullable = false)
    private Long plantId;

    @Builder
    private Reservation(int count, int price, Member member, Long plantId) {
        this.count = count;
        this.price = price;
        this.member = member;
        this.plantId = plantId;
    }

    // -- 비즈니스 로직
    public void editReservation(int count, int price) {
        this.count = count;
        this.price = price;
    }
}
