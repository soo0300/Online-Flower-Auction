package com.kkoch.admin.domain.auction;

import com.kkoch.admin.domain.TimeBaseEntity;
import com.kkoch.admin.domain.admin.Admin;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Auction extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auction_id")
    private Long id;

    @Column(nullable = false)
    private int code;
    @Column(nullable = false)
    private LocalDateTime startTime;
    @Column(nullable = false)
    private boolean active;
    @Enumerated(STRING)
    @Column(length = 20)
    private Status status;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @Builder
    private Auction(int code, LocalDateTime startTime, boolean active, Status status, Admin admin) {
        this.code = code;
        this.startTime = startTime;
        this.active = active;
        this.title = title;
        this.status = status;
        this.admin = admin;
    }
}
