package com.kkoch.admin.api.service.category;

import com.kkoch.admin.IntegrationTestSupport;
import com.kkoch.admin.api.controller.category.response.CategoryResponse;
import com.kkoch.admin.api.service.category.dto.AddCategoryDto;
import com.kkoch.admin.api.service.category.dto.SetCategoryDto;
import com.kkoch.admin.domain.plant.Category;
import com.kkoch.admin.domain.plant.repository.CategoryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.tuple;

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

    @DisplayName("카테고리를 선택시 하위 카테고리를 가져온다.")
    @Test
    void getCategories() throws Exception {
        //given
        Category parentCatgory = createRootCategory("절화");
        Category catgory1 = createCategory("장미", parentCatgory);
        Category catgory2 = createCategory("튤립", parentCatgory);
        //when

        List<CategoryResponse> results = categoryService.getCategories(parentCatgory.getId());

        //then
        Assertions.assertThat(results).hasSize(2)
                .extracting("name", "categoryId", "level")
                .containsExactlyInAnyOrder(
                        tuple(catgory1.getName(), catgory1.getId(), catgory1.getLevel()),
                        tuple(catgory2.getName(), catgory2.getId(), catgory2.getLevel())
                );
    }

    @DisplayName("관계자는 카테고리를 선택해 이름을 변경 할 수 있다.")
    @Test
    void setCategory() throws Exception {
        //given
        Category parentCategory = createRootCategory("절화");
        Category category1 = createCategory("장미", parentCategory);
        Category category2 = createCategory("튤립", parentCategory);

        SetCategoryDto setCategoryDto = SetCategoryDto.builder()
                .categoryId(category1.getId())
                .changeName("바뀐 장미")
                .build();

        //when
        Category resultCategory = categoryService.setCategory(setCategoryDto);

        //then
        Assertions.assertThat(resultCategory.getName()).isEqualTo(category1.getName());
    }


    private Category createRootCategory(String name) {
        Category category = Category.builder()
                .name(name)
                .level(1)
                .active(true)
                .build();

        return categoryRepository.save(category);
    }

    private Category createCategory(String name, Category parent) {
        Category category = Category.builder()
                .name(name)
                .level(1)
                .parent(parent)
                .active(true)
                .build();

        return categoryRepository.save(category);
    }

}