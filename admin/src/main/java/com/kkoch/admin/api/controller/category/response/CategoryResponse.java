package com.kkoch.admin.api.controller.category.response;

import lombok.Builder;
import lombok.Data;

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

}
