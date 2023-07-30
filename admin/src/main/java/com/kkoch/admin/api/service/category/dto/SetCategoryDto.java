package com.kkoch.admin.api.service.category.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class SetCategoryDto {

    private Long categoryId;

    private String changeName;

    @Builder
    public SetCategoryDto(Long categoryId, String changeName) {
        this.categoryId = categoryId;
        this.changeName = changeName;
    }
}
