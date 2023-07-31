package com.kkoch.user.domain.reservation;

import com.kkoch.user.domain.TimeBaseEntity;
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

    private Long memberId;

    @Column(nullable = false)
    private Long plantId;

    @Builder
    private Reservation(int count, int price, Long memberId, Long plantId) {
        this.count = count;
        this.price = price;
        this.memberId = memberId;
        this.plantId = plantId;
    }
}
