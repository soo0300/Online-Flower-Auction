package com.kkoch.admin.domain.plant.repository;

import com.kkoch.admin.api.controller.stats.response.AuctionArticleForStatsResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class StatsQueryRepository {

    private final JPAQueryFactory queryFactory;

    public StatsQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<AuctionArticleForStatsResponse> findByBidTime() {
        return null;
    }
}
