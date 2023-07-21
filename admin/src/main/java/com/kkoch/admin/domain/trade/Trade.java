package com.kkoch.admin.domain.trade;

import com.kkoch.admin.domain.TimeBaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Trade extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trade_id")
    private Long id;

    @Column(nullable = false)
    private int totalPrice;

    @Column(nullable = false)
    private LocalDateTime tradeDate;

    @Column(nullable = false)
    private boolean active;

    private Long memberId;

    @Builder
    public Trade(int totalPrice, LocalDateTime tradeDate, boolean active, Long memberId) {
        this.totalPrice = totalPrice;
        this.tradeDate = tradeDate;
        this.active = active;
        this.memberId = memberId;
    }
}
