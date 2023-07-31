package com.kkoch.admin.api.controller.category.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@Data
public class SetCategoryRequest {

    @NotEmpty
    private String changeName;

    @Builder
    public SetCategoryRequest(String changeName) {
        this.changeName = changeName;
    }


}
