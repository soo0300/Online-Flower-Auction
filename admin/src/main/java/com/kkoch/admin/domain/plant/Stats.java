package com.kkoch.admin.domain.plant;

import com.kkoch.admin.domain.Grade;
import com.kkoch.admin.domain.TimeBaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stats extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stats_id")
    private Long id;

    @Column(nullable = false)
    private int priceAvg;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false, length = 20)
    private Grade grade;

    @Column(nullable = false)
    private int count;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plant_id")
    private Plant plant;

    @Builder
    private Stats(int priceAvg, Grade grade, int count, Plant plant) {
        this.priceAvg = priceAvg;
        this.grade = grade;
        this.count = count;
        this.plant = plant;
    }
}
