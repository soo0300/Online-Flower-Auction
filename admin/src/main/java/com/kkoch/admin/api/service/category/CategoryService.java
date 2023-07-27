package com.kkoch.admin.api.service.category;

import com.kkoch.admin.api.service.category.dto.AddCategoryDto;
import com.kkoch.admin.domain.plant.Category;
import com.kkoch.admin.domain.plant.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Long addCategory(AddCategoryDto dto) {

        Category category = dto.toEntity();
        setParentCategory(category, dto.getParentId());
        return categoryRepository.save(category).getId();
    }

    private Category getCategoryEntity(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 카테고리 ID=" + categoryId));
    }

    private void setParentCategory(Category category, Long parentId) {
        if (parentId != null) {
            Category parent = getCategoryEntity(parentId);
            category.changeParent(parent);
        }
    }

}
