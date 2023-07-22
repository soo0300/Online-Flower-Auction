package com.kkoch.admin.domain.trade.repository;

import com.kkoch.admin.api.controller.trade.response.TradeResponse;
import com.kkoch.admin.domain.trade.repository.dto.TradeSearchCond;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

import static com.kkoch.admin.domain.trade.QTrade.*;

@Repository
public class TradeQueryRepository {

    private final JPAQueryFactory queryFactory;

    public TradeQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<TradeResponse> findByCondition(Long memberId, TradeSearchCond cond, Pageable pageable) {
        List<Long> ids = queryFactory
                .select(trade.id)
                .from(trade)
                .where(
                        trade.memberId.eq(memberId),
                        trade.active.isTrue(),
                        trade.tradeDate.between(cond.getStartDateTime(), cond.getEndDateTime())
                )
                .orderBy(trade.createdDate.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        if (CollectionUtils.isEmpty(ids)) {
            return new ArrayList<>();
        }

        return queryFactory
                .select(Projections.constructor(TradeResponse.class,
                        trade.id,
                        trade.totalPrice,
                        trade.tradeDate,
                        trade.pickupStatus,
                        trade.articles.size()
                ))
                .from(trade)
                .where(trade.id.in(ids))
                .orderBy(trade.createdDate.desc())
                .fetch();
    }
}
