package com.kkoch.admin.api.service.category;

import com.kkoch.admin.domain.plant.Category;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AddCategoryDto {

    private String name;

    private int depth;

    private Long parentId;

    @Builder
    public AddCategoryDto(String name, int depth, Long parentId) {
        this.name = name;
        this.depth = depth;
        this.parentId = parentId;
    }
}
