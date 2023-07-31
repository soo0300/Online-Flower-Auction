package com.kkoch.admin.api.service.category;

import com.kkoch.admin.api.controller.category.response.CategoryResponse;
import com.kkoch.admin.api.service.category.dto.AddCategoryDto;
import com.kkoch.admin.api.service.category.dto.SetCategoryDto;
import com.kkoch.admin.domain.plant.Category;
import com.kkoch.admin.domain.plant.repository.CategoryRepository;
import com.kkoch.admin.domain.plant.repository.PlantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private final PlantRepository plantRepository;

    public Long addCategory(AddCategoryDto dto) {
        Category findCategory = getCategoryEntity(dto.getParentId());
        Category category = findCategory.createCategory(dto.getName());

        if (isSubdivision(category.getLevel()))
            addPlant(category);

        return category.getId();
    }

    private void addPlant(Category category) {

    }

    public CategoryResponse setCategory(Long categoryId, SetCategoryDto setCategoryDto) {
        Category category = getCategoryEntity(categoryId);
        category.changeCategory(setCategoryDto.getChangeName());
        return CategoryResponse.of(category);
    }

    public Long removeCategory(Long categoryId) {
        Category category = getCategoryEntity(categoryId);
        category.remove();

        removeAllSubCategories(category);

        return category.getId();
    }

    private void removeAllSubCategories(Category category) {
        List<Category> subCategories = category.getChildren();

        if (subCategories.isEmpty()) return;

        for (Category subCategory : subCategories) {
            subCategory.remove();
        }
    }

    public List<CategoryResponse> getCategories(Long parentId) {
        return categoryRepository.findAllById(parentId)
                .stream().map(CategoryResponse::new).collect(Collectors.toList());

    }

    private Category getCategoryEntity(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 카테고리 ID=" + categoryId));
    }

    private boolean isSubdivision(int level) {
        return level == 3;
    }
}
