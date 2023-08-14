package com.kkoch.user.domain.pointhistory.repository;

import com.kkoch.user.api.controller.pointhistory.response.PointHistoryResponse;
import com.kkoch.user.domain.member.QMember;
import com.kkoch.user.domain.pointhistory.QPointHistory;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.jsonwebtoken.lang.Collections;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

import static com.kkoch.user.domain.member.QMember.member;
import static com.kkoch.user.domain.pointhistory.QPointHistory.*;

@Repository
public class PointHistoryQueryRepository {

    private final JPAQueryFactory queryFactory;

    public PointHistoryQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<PointHistoryResponse> findByMemberKey(String memberKey, Pageable pageable) {
        List<Long> ids = queryFactory
            .select(pointHistory.id)
            .from(pointHistory)
            .join(pointHistory.member, member)
            .where(
                pointHistory.member.memberKey.eq(memberKey)
            )
            .orderBy(pointHistory.createdDate.desc())
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset())
            .fetch();

        if (Collections.isEmpty(ids)) {
            return new ArrayList<>();
        }

        return queryFactory
            .select(Projections.constructor(PointHistoryResponse.class,
                pointHistory.bank,
                pointHistory.amount,
                pointHistory.status,
                pointHistory.createdDate
            ))
            .from(pointHistory)
            .where(pointHistory.id.in(ids))
            .orderBy(pointHistory.createdDate.desc())
            .fetch();
    }

    public long getTotalCount(String memberKey) {
        return queryFactory
            .select(pointHistory.id)
            .from(pointHistory)
            .join(pointHistory.member, member)
            .where(
                pointHistory.member.memberKey.eq(memberKey)
            )
            .fetch()
            .size();
    }
}
