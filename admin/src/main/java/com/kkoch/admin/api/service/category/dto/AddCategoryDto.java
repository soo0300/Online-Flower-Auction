package com.kkoch.admin.api.service.category.dto;

import com.kkoch.admin.domain.plant.Category;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AddCategoryDto {

    private String name;

    private Long parentId;

    private int level;

    @Builder
    public AddCategoryDto(String name, Long parentId, int level) {
        this.name = name;
        this.parentId = parentId;
        this.level = level;
    }

    public Category toEntity() {
        return Category.builder()
                .name(this.name)
                .level(this.level)
                .build();
    }
}
