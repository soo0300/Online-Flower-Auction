package com.kkoch.admin.domain.notice.repository;

import com.kkoch.admin.api.controller.notice.response.NoticeResponse;
import com.kkoch.admin.domain.notice.repository.dto.NoticeSearchCond;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static com.kkoch.admin.domain.notice.QNotice.*;
import static org.springframework.util.StringUtils.*;

@Repository
public class NoticeQueryRepository {

    private final JPAQueryFactory queryFactory;

    public NoticeQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<NoticeResponse> getNoticeByCondition(NoticeSearchCond cond, Pageable pageable) {
        List<Long> ids = queryFactory
                .select(notice.id)
                .from(notice)
                .where(
                        notice.active.isTrue(),
                        eqTitle(cond.getTitle()),
                        eqContent(cond.getContent())
                )
                .orderBy(notice.createdDate.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        if (CollectionUtils.isEmpty(ids)) {
            return new ArrayList<>();
        }

        return queryFactory
                .select(Projections.constructor(NoticeResponse.class,
                        notice.title,
                        notice.content,
                        notice.createdDate
                ))
                .from(notice)
                .where(notice.id.in(ids))
                .orderBy(notice.createdDate.desc())
                .fetch();
    }

    public long getTotalCount(NoticeSearchCond cond) {
        return queryFactory
                .select(notice.id)
                .from(notice)
                .where(
                        notice.active.isTrue(),
                        eqTitle(cond.getTitle()),
                        eqContent(cond.getContent())
                )
                .fetch()
                .size();
    }

    private BooleanExpression eqTitle(String title) {
        return hasText(title) ? notice.title.like("%" + title + "%") : null;
    }

    private BooleanExpression eqContent(String content) {
        return hasText(content) ? notice.content.like("%" + content + "%") : null;
    }
}
