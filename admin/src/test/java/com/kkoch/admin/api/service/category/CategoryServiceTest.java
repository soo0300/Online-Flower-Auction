package com.kkoch.admin.api.service.category;

import com.kkoch.admin.IntegrationTestSupport;
import com.kkoch.admin.api.service.category.dto.AddCategoryDto;
import com.kkoch.admin.domain.plant.Category;
import com.kkoch.admin.domain.plant.repository.CategoryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CategoryServiceTest extends IntegrationTestSupport {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @DisplayName("관계자는 카테고리를 등록 할 수 있다.")
    @Test
    void addSubCategory() throws Exception {
        //given
        // 부모카데고리 등록
        Category parentCategory = Category.builder()
                .name("절화")
                .level(1)
                .build();

        Long parentId = 1L;

        ReflectionTestUtils.setField(parentCategory, "id", parentId);

        categoryRepository.save(parentCategory);

        AddCategoryDto dto = AddCategoryDto.builder()
                .name("장미")
                .parentId(parentId)
                .build();

        Long fakeId = 2L;
        Category subCategory = dto.toEntity();

        ReflectionTestUtils.setField(subCategory, "id", fakeId);

        //when
        categoryService.addCategory(dto);
        List<Category> categories = categoryRepository.findAll();

        //then
        Assertions.assertThat(categories).hasSize(2);

    }

}