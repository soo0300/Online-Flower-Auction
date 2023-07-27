package com.kkoch.admin.api.controller.category.request;

import com.kkoch.admin.api.service.category.dto.AddCategoryDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@Data
public class AddCategoryRequest {

    @NotEmpty
    private String name;

    private Long parentId;

    @NonNull
    private int level;

    @Builder
    private AddCategoryRequest(String name, Long parentId, int level) {
        this.name = name;
        this.parentId = parentId;
        this.level = level;
    }

    public AddCategoryDto toAddCategoryDto() {
        return AddCategoryDto.builder()
                .name(this.name)
                .level(this.level)
                .build();
    }

}
