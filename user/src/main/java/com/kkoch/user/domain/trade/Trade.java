package com.kkoch.user.domain.trade;

import com.kkoch.user.domain.TimeBaseEntity;
import com.kkoch.user.domain.member.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Trade extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trade_id")
    private Long id;

    private int totalPrice;
    private LocalDateTime tradeDate;
    private boolean active;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    private Trade(int totalPrice, LocalDateTime tradeDate, boolean active, Member member) {
        this.totalPrice = totalPrice;
        this.tradeDate = tradeDate;
        this.active = active;
        this.member = member;
    }
}
