package com.kkoch.admin.api.controller.category;


import com.kkoch.admin.api.ApiResponse;
import com.kkoch.admin.api.controller.category.request.AddCategoryRequest;
import com.kkoch.admin.api.controller.category.response.CategoryResponse;
import com.kkoch.admin.api.service.category.CategoryService;
import com.kkoch.admin.api.service.category.dto.AddCategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @GetMapping("/{parentId}")
    public ApiResponse<List<CategoryResponse>> getCategories(@PathVariable Long parentId) {

        return ApiResponse.ok(categoryService.getCategories(parentId));
    }
}
