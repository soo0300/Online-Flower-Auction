package com.kkoch.admin.domain.auction.repository;

import com.kkoch.admin.api.controller.auction.response.AuctionResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.kkoch.admin.domain.auction.QAuction.auction;

@Repository
public class AuctionQueryRepository {

    private final JPAQueryFactory queryFactory;

    public AuctionQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<AuctionResponse> findAllAuction() {
        return queryFactory
                .select(Projections.constructor(AuctionResponse.class,
                        auction.id,
                        auction.code,
                        auction.status,
                        auction.startTime,
                        auction.createdDate))
                .from(auction)
                .where(auction.active.eq(true)).fetch();
    }
}
