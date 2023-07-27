package com.kkoch.admin.api.controller.category.response;

import com.kkoch.admin.domain.plant.Category;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class CategoryResponse {

    private Long categoryId;
    private String name;
    private int level;

    @Builder
    private CategoryResponse(Long categoryId, String name, int level) {
        this.categoryId = categoryId;
        this.name = name;
        this.level = level;
    }
    // ??
    public CategoryResponse(Category entity){
        this.categoryId = entity.getId();
        this.name = entity.getName();
        this.level = entity.getLevel();

    }

}
