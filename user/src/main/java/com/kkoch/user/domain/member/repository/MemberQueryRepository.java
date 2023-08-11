package com.kkoch.user.domain.member.repository;

import com.kkoch.user.api.controller.member.response.MemberInfoResponse;
import com.kkoch.user.domain.member.QMember;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import static com.kkoch.user.domain.member.QMember.*;

@Repository
public class MemberQueryRepository {

    private final JPAQueryFactory queryFactory;

    public MemberQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public MemberInfoResponse findMyInfoByMemberKey(String memberKey) {
        return queryFactory
            .select(Projections.constructor(MemberInfoResponse.class,
                member.email,
                member.name,
                member.tel,
                member.businessNumber
            ))
            .from(member)
            .where(member.memberKey.eq(memberKey))
            .fetchFirst();
    }

    public Boolean existEmail(String email) {
        Integer content = queryFactory
            .selectOne()
            .from(member)
            .where(member.email.eq(email))
            .fetchFirst();

        return content != null;
    }
}
