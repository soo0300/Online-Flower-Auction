package com.kkoch.admin.domain.plant.repository;

import com.kkoch.admin.domain.plant.Category;
import com.kkoch.admin.domain.plant.QCategory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class CategoryQueryRepository {

    private final JPAQueryFactory queryFactory;

    public CategoryQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<Category> getCategories(String parentName) {
        QCategory code = new QCategory("code");
        QCategory name = new QCategory("name");
        return queryFactory
            .select(name)
            .from(name)
            .join(name.parent, code).fetchJoin()
            .where(
                name.active.isTrue(),
                name.parent.name.eq(parentName)
            )
            .fetch();
    }

}
