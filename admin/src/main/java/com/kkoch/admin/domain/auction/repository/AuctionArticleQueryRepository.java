package com.kkoch.admin.domain.auction.repository;

import com.kkoch.admin.api.controller.trade.response.AuctionArticleResponse;
import com.kkoch.admin.domain.plant.QCategory;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.kkoch.admin.domain.auction.QAuctionArticle.*;
import static com.kkoch.admin.domain.plant.QPlant.*;

@Repository
public class AuctionArticleQueryRepository {

    private final JPAQueryFactory queryFactory;

    public AuctionArticleQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<AuctionArticleResponse> findByTradeId(Long tradeId) {
        QCategory code = new QCategory("code");
        QCategory name = new QCategory("name");
        QCategory type = new QCategory("type");
        return queryFactory
                .select(Projections.constructor(AuctionArticleResponse.class,
                        auctionArticle.plant.code.name,
                        auctionArticle.plant.name.name,
                        auctionArticle.plant.type.name,
                        auctionArticle.grade,
                        auctionArticle.count,
                        auctionArticle.bidPrice,
                        auctionArticle.bidTime,
                        auctionArticle.region
                ))
                .from(auctionArticle)
                .join(auctionArticle.plant, plant)
                .join(plant.code, code)
                .join(plant.name, name)
                .join(plant.type, type)
                .where(auctionArticle.trade.id.eq(tradeId))
                .fetch();
    }

    public int getAuctionArticle(Long auctionId) {
        return queryFactory.select(auctionArticle.id)
                .from(auctionArticle)
                .where(auctionArticle.auction.id.eq(auctionId))
                .fetch()
                .size();
    }
}
