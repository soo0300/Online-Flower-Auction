package com.kkoch.admin.api.service.category;

import com.kkoch.admin.IntegrationTestSupport;
import com.kkoch.admin.api.controller.category.response.CategoryResponse;
import com.kkoch.admin.api.service.category.dto.AddCategoryDto;
import com.kkoch.admin.api.service.category.dto.SetCategoryDto;
import com.kkoch.admin.domain.plant.Category;
import com.kkoch.admin.domain.plant.repository.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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
        assertThat(categories).hasSize(2);

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
        assertThat(results).hasSize(2)
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
                .changeName("바뀐 장미")
                .build();

        //when
        CategoryResponse resultCategory = categoryService.setCategory(category1.getId(), setCategoryDto);

        //then
        Optional<Category> findCategory = categoryRepository.findById(category1.getId());
        assertThat(findCategory).isPresent();
        assertThat(findCategory.get().getName()).isEqualTo(resultCategory.getName());
    }

    @DisplayName("관계자는 카테고리를 선택해 삭제할 수 있다. 카테고리 삭제시 하위 카테고리 모두 삭제된다.")
    @Test
    void removeCategory() throws Exception {
        //given
        Category parentCategory = createRootCategory("절화");
        Category category = createCategory("장미", parentCategory);
        Category subCategory = createCategory("거베라", category);

        //when
        Long categoryId = categoryService.removeCategory(category.getId());

        //then
        Optional<Category> findCategory = categoryRepository.findById(categoryId);
        Optional<Category> findSubCategory = categoryRepository.findById(subCategory.getId());
        assertThat(findCategory).isPresent();
        assertThat(findSubCategory).isPresent();
        assertThat(findCategory.get().isActive()).isFalse();
        assertThat(findSubCategory.get().isActive()).isFalse();

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