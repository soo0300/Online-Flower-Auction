package com.kkoch.admin.domain.plant.repository;

import com.kkoch.admin.api.controller.plant.response.PlantResponse;
import com.kkoch.admin.api.controller.trade.response.TradeResponse;
import com.kkoch.admin.domain.plant.repository.dto.PlantSearchCond;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.kkoch.admin.domain.trade.QTrade.trade;

@Repository
public class PlantQueryRepository {
    private final JPAQueryFactory queryFactory;

    public PlantQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<PlantResponse> findByCondition(PlantSearchCond cond) {
        return null;
    }
}
