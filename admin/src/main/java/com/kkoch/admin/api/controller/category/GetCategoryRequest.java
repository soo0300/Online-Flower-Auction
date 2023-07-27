package com.kkoch.admin.api.controller.category;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class GetCategoryRequest {

    @NonNull
    private String name;

    @NonNull
    private Long parentId;

    @Builder
    private GetCategoryRequest(String name, Long parentId) {
        this.name = name;
        this.parentId = parentId;
    }
}
