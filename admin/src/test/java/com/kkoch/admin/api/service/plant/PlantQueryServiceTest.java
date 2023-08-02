package com.kkoch.admin.api.service.plant;

import com.kkoch.admin.IntegrationTestSupport;
import com.kkoch.admin.api.controller.plant.response.PlantResponse;
import com.kkoch.admin.api.service.plant.dto.PlantSearchCond;
import com.kkoch.admin.domain.plant.Category;
import com.kkoch.admin.domain.plant.Plant;
import com.kkoch.admin.domain.plant.repository.CategoryRepository;
import com.kkoch.admin.domain.plant.repository.PlantQueryRepository;
import com.kkoch.admin.domain.plant.repository.PlantRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@Transactional
class PlantQueryServiceTest extends IntegrationTestSupport {

    @Autowired
    private PlantQueryRepository plantQueryRepository;

    @Autowired
    private PlantQueryService plantQueryService;

    @Autowired
    private PlantRepository plantRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @DisplayName("관계자는 부류, 품목, 품종별로 식물을 검색할 수 있다.")
    @Test
    void getCategories() throws Exception {
        //given
        Plant plant1 = createPlant(createCategory("절화"), createCategory("장미"), createCategory("거베라"));
        Plant plant2 = createPlant(createCategory("절화"), createCategory("장미"), createCategory("빨강"));
        Plant plant3 = createPlant(createCategory("절화"), createCategory("장미"), createCategory("노랑"));
        Plant plant4 = createPlant(createCategory("절화"), createCategory("수국"), createCategory("하양"));

        //when
        PlantSearchCond plantSearchCond = PlantSearchCond.builder()
                .code("절화")
                .type("장미")
                .build();

        List<PlantResponse> results = plantQueryService.getPlants(plantSearchCond);

        //then
        assertThat(results).hasSize(3)
                .extracting("code", "type", "name", "plantId")
                .containsExactlyInAnyOrder(
                        tuple(plant1.getCode().getName(), plant1.getType().getName(), plant1.getName().getName(), plant1.getId()),
                        tuple(plant2.getCode().getName(), plant2.getType().getName(), plant2.getName().getName(), plant2.getId()),
                        tuple(plant3.getCode().getName(), plant3.getType().getName(), plant3.getName().getName(), plant3.getId())
                );
    }

    private Plant createPlant(Category code, Category type, Category name) {
        Plant plant = Plant.builder()
                .code(code)
                .type(type)
                .name(name)
                .active(true)
                .build();

        return plantRepository.save(plant);
    }

    private Category createCategory(String name) {
        Category category = Category.builder()
                .name(name)
                .level(1)
                .active(true)
                .build();

        return categoryRepository.save(category);
    }

}