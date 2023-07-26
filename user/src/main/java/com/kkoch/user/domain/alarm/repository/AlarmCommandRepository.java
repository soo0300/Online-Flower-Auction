package com.kkoch.user.domain.alarm.repository;

import com.kkoch.user.domain.alarm.QAlarm;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import static com.kkoch.user.domain.alarm.QAlarm.alarm;

@Repository
public class AlarmCommandRepository {

    private final JPAQueryFactory queryFactory;

    public AlarmCommandRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public int updateOpen(Long memberId) {
        return (int) queryFactory
            .update(alarm)
            .where(
                alarm.member.id.eq(memberId),
                alarm.open.isFalse()
            )
            .set(alarm.open, true)
            .execute();
    }
}
