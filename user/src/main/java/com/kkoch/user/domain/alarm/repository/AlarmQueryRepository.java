package com.kkoch.user.domain.alarm.repository;

import com.kkoch.user.api.controller.alarm.response.AlarmResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.kkoch.user.domain.alarm.QAlarm.*;
import static com.kkoch.user.domain.member.QMember.*;

@Repository
public class AlarmQueryRepository {

    private final JPAQueryFactory queryFactory;

    public AlarmQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<AlarmResponse> findAlarmsByMemberKey(String memberKey) {
        return queryFactory
            .select(Projections.constructor(AlarmResponse.class,
                alarm.id,
                alarm.content,
                alarm.open,
                alarm.createdDate
            ))
            .from(alarm)
            .join(alarm.member, member)
            .where(alarm.member.memberKey.eq(memberKey))
            .orderBy(alarm.createdDate.desc())
            .limit(10)
            .offset(0)
            .fetch();
    }
}
