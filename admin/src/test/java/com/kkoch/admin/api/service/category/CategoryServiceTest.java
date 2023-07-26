package com.kkoch.admin.api.service.category;

import com.kkoch.admin.IntegrationTestSupport;
import com.kkoch.admin.domain.plant.Category;
import com.kkoch.admin.domain.plant.repository.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class CategoryServiceTest extends IntegrationTestSupport {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @DisplayName("카테고리 등록시 중복 오류")
    @Test
    public void addCategoryDuplicationError() throws Exception {
        //given
        AddCategoryDto request = AddCategoryDto.builder()
                .name("절화")
                .build();

        Category categoryEntity = request.toEntity();

        Long fakeCategoryId = 1L;

        // private 값을 직접 넣을수 있다.
        ReflectionTestUtils.setField(categoryEntity, "id", fakeCategoryId);

        //mocking
        given(categoryRepository.save(Mockito.any()))
                .willReturn(categoryEntity);
        given(categoryRepository.findById(fakeCategoryId))
                .willReturn(Optional.of(categoryEntity));

        //when
        Long newCategoryId = categoryService.addCategory(request);

        //then
        Category findCategory = categoryRepository.findById(newCategoryId).get();

        assertThat(findCategory.getName()).isEqualTo(categoryEntity.getName());
        assertThat(findCategory.getId()).isEqualTo(categoryEntity.getId());
    }

}