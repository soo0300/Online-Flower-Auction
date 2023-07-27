package com.kkoch.admin.api.controller.category;


import com.kkoch.admin.api.ApiResponse;
import com.kkoch.admin.api.controller.category.request.AddCategoryRequest;
import com.kkoch.admin.api.service.category.CategoryService;
import com.kkoch.admin.api.service.category.dto.AddCategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/admin-service/categories")
@RestController
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ApiResponse<Long> addCategory(@Valid @RequestBody AddCategoryRequest request) {

        AddCategoryDto dto = request.toAddCategoryDto();
        Long result = categoryService.addCategory(dto);

        return ApiResponse.ok(result);
    }
}
