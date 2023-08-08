package com.kkoch.admin.domain.plant.repository;

import com.kkoch.admin.api.controller.plant.response.PlantResponse;
import com.kkoch.admin.api.service.plant.dto.PlantSearchCond;
import com.kkoch.admin.domain.plant.Plant;
import com.kkoch.admin.domain.plant.QCategory;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.kkoch.admin.domain.plant.QPlant.plant;
import static org.springframework.util.StringUtils.hasText;

@Repository
public class PlantQueryRepository {
    private final JPAQueryFactory queryFactory;

    public PlantQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<PlantResponse> findByCondition(PlantSearchCond cond) {
        return queryFactory
                .select(Projections.constructor(PlantResponse.class,
                        plant.id,
                        plant.code.name,
                        plant.type.name,
                        plant.name.name))
                .from(plant)
                .where(
                        plant.active.isTrue(),
                        codeEq(cond.getCode()),
                        typeEq(cond.getType()),
                        nameEq(cond.getName()))
                .fetch();

    }

    public List<Plant> getPlants() {
        return queryFactory
                .select(plant)
                .from(plant)
                .where(plant.active.isTrue())
                .fetch();
    }

    public Long findPlantId(String type, String name) {
        QCategory qtype = new QCategory("type");
        QCategory qname = new QCategory("name");

        return queryFactory
                .select(plant.id)
                .from(plant)
                .join(plant.type, qtype)
                .join(plant.name, qname)
                .where(
                        plant.type.name.eq(type),
                        plant.name.name.eq(name)
                )
                .fetchFirst();
    }

    private BooleanExpression nameEq(String name) {
        return hasText(name) ? plant.name.name.eq(name) : null;
    }

    private BooleanExpression typeEq(String type) {
        return hasText(type) ? plant.type.name.eq(type) : null;
    }

    private BooleanExpression codeEq(String code) {
        return hasText(code) ? plant.code.name.eq(code) : null;
    }

}
