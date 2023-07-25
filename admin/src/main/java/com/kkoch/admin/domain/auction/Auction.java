package com.kkoch.admin.domain.auction;

import com.kkoch.admin.domain.TimeBaseEntity;
import com.kkoch.admin.domain.admin.Admin;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import static com.kkoch.admin.domain.auction.Status.OPEN;
import static com.kkoch.admin.domain.auction.Status.READY;
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
        this.status = status;
        this.admin = admin;
    }

    //연관관계 편의 메서드
    public static Auction toEntity(Long id) {
        Auction auction = Auction.builder().build();
        auction.id = id;
        return auction;
    }

    public static Auction toEntity(int code, LocalDateTime startTime, Admin admin) {

        return Auction.builder()
                .code(code)
                .startTime(startTime)
                .active(true)
                .status(READY)
                .admin(admin)
                .build();
    }

    // 비지니스 로직
    public String getTitle() {
        String type = null;
        if (this.code == 1) {
            type = "절화";
        } else if (this.code == 2) {
            type = "난";
        } else if (this.code == 3) {
            type = "관엽";
        } else if (this.code == 4) {
            type = "춘화";
        }

        return this.startTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)) + " " + type + " " + this.status.getText();
    }

    public void changeStatus(Status status) {
        this.status = status;
    }

    public void changeAuction(int code, LocalDateTime startTime, Admin admin) {
        this.code = code;
        this.startTime = startTime;
        this.admin = admin;
    }
}
