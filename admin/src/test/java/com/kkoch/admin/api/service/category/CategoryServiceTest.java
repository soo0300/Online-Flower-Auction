package com.kkoch.admin.api.service.category;

import com.kkoch.admin.IntegrationTestSupport;
import com.kkoch.admin.domain.plant.repository.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class CategoryServiceTest extends IntegrationTestSupport {

    @Autowired
    private CategoryRepository categoryRepository;

    @DisplayName("카테고리 등록시 중복 오류")
    @Test
    public void addCategoryDuplicationError() throws Exception {
        //given
        AddCategoryDto addCategoryDto = AddCategoryDto.builder()
                .name("절화")
                .depth(1)
                .parentId(null)
                .build();

        //when

        //then
    }

}