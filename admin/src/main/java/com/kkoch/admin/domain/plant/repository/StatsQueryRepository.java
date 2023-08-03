package com.kkoch.admin.domain.plant.repository;

import com.kkoch.admin.api.service.stats.dto.AuctionArticleForStatsDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static com.kkoch.admin.domain.auction.QAuctionArticle.auctionArticle;
import static com.kkoch.admin.domain.plant.QPlant.plant;

@Repository
public class StatsQueryRepository {

    private final JPAQueryFactory queryFactory;

    public StatsQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<AuctionArticleForStatsDto> findByTime() {
        return queryFactory
                .select(Projections.constructor(AuctionArticleForStatsDto.class,
                        auctionArticle.plant.id,
                        auctionArticle.grade,
                        auctionArticle.count,
                        auctionArticle.bidPrice,
                        auctionArticle.bidTime
                )).from(auctionArticle)
                .join(auctionArticle.plant, plant)
                .where(auctionArticle.bidPrice.ne(0),
                        auctionArticle.bidTime.between(LocalDateTime.now().minusHours(12), LocalDateTime.now()))
                .orderBy(auctionArticle.bidPrice.desc())
                .fetch();
    }
}
