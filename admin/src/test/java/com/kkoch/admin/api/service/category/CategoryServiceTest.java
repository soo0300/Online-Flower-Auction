package com.kkoch.admin.api.service.category;

import com.kkoch.admin.IntegrationTestSupport;
import com.kkoch.admin.api.service.category.dto.AddCategoryDto;
import com.kkoch.admin.domain.plant.Category;
import com.kkoch.admin.domain.plant.repository.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CategoryServiceTest extends IntegrationTestSupport {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @DisplayName("최상위 카테고리를 등록 성공")
    @Test
    void addRootCategory() throws Exception {
        //given
        AddCategoryDto dto = AddCategoryDto.builder()
                .name("절화")
                .level(1)
                .build();

        Category categoryEntity = dto.toEntity();

        Long fakeCategoryId = 1L;

//         private 값을 직접 넣을수 있다.
        ReflectionTestUtils.setField(categoryEntity, "id", fakeCategoryId);

        //when
        Long newCategoryId = categoryService.addCategory(dto);

        //then
        Optional<Category> findCategory = categoryRepository.findById(newCategoryId);

        assertThat(findCategory).isPresent();
        assertThat(findCategory.get().getName()).isEqualTo(categoryEntity.getName());
        assertThat(findCategory.get().getLevel()).isEqualTo(categoryEntity.getLevel());
        assertThat(findCategory.get().getId()).isEqualTo(categoryEntity.getId());
    }

    @DisplayName("중, 소 카테고리 등록 성공(카테고리 등록식 부모 카테고리도 설정)")
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
                .level(2)
                .parentId(parentId)
                .build();

        Long fakeId = 2L;
        Category subCategory = dto.toEntity();

        ReflectionTestUtils.setField(subCategory, "id", fakeId);

        //when
        Long newCategoryId = categoryService.addCategory(dto);

        //then
        Optional<Category> findCategory = categoryRepository.findById(newCategoryId);

        assertThat(findCategory).isPresent();
        assertThat(findCategory.get().getId()).isEqualTo(subCategory.getId());
        assertThat(findCategory.get().getName()).isEqualTo(subCategory.getName());

    }

}