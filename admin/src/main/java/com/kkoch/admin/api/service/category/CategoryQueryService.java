package com.kkoch.admin.api.service.category;

import com.kkoch.admin.api.controller.category.response.CategoryForMemberResponse;
import com.kkoch.admin.domain.plant.Category;
import com.kkoch.admin.domain.plant.repository.CategoryQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CategoryQueryService {


    private final CategoryQueryRepository categoryQueryRepository;

    public List<String> getTypesForMember(String code) {
        return categoryQueryRepository.getTypes(code);
    }

    public List<String> getNamesForMember(String code, String type) {
        return categoryQueryRepository.getNames(code, type);
    }

    public List<CategoryForMemberResponse> getCategories(String parentName) {
        List<Category> categories = categoryQueryRepository.getCategories(parentName);

        return categories.stream()
            .map(CategoryForMemberResponse::of)
            .collect(Collectors.toList());
    }
}
