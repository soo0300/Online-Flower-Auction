package com.kkoch.admin.domain.auction;

import com.kkoch.admin.domain.Grade;
import com.kkoch.admin.domain.TimeBaseEntity;
import com.kkoch.admin.domain.plant.Plant;
import com.kkoch.admin.domain.trade.Trade;
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
public class AuctionArticle extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auction_article_id")
    private Long id;

    @Column(length = 10, nullable = false)
    private String auctionNumber;
    @Enumerated(STRING)
    @Column(length = 20, nullable = false)
    private Grade grade;
    @Column(nullable = false)
    private int count;
    @Column(nullable = false)
    private int price;
    @Column(nullable = false)
    private LocalDateTime bidTime;
    @Column(length = 20, nullable = false)
    private String region;
    @Column(length = 20, nullable = false)
    private String shipper;
    @Column(nullable = false)
    private int startPrice;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "plant_id")
    private Plant plant;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "auction_id")
    private Auction auction;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "trade_id")
    private Trade trade;

    @Builder
    public AuctionArticle(String auctionNumber, Grade grade, int count, int price, LocalDateTime bidTime, String region, String shipper, int startPrice, Plant plant, Auction auction, Trade trade) {
        this.auctionNumber = auctionNumber;
        this.grade = grade;
        this.count = count;
        this.price = price;
        this.bidTime = bidTime;
        this.region = region;
        this.shipper = shipper;
        this.startPrice = startPrice;
        this.plant = plant;
        this.auction = auction;
        this.trade = trade;
    }
}
