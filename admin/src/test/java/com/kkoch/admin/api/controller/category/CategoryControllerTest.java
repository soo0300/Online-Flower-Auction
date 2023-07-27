package com.kkoch.admin.api.controller.category;


import com.kkoch.admin.ControllerTestSupport;
import com.kkoch.admin.api.controller.category.request.AddCategoryRequest;
import com.kkoch.admin.api.service.category.CategoryService;
import com.kkoch.admin.domain.plant.repository.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {CategoryController.class})
class CategoryControllerTest extends ControllerTestSupport {

    final String URI = "/admin-service/categories";
    @MockBean
    private CategoryService categoryService;
    @MockBean
    private CategoryRepository categoryRepository;

    @DisplayName("관계자는 최상위 카테고리를 등록할 수 있다.")
    @Test
    void addRootCategory() throws Exception {
        //given
        AddCategoryRequest request = AddCategoryRequest.builder()
                .name("절화")
                .level(1)
                .build();

        //when, then
        mockMvc.perform(post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("SUCCESS"))
                .andExpect(jsonPath("$.data").isNumber());
    }

    @DisplayName("관계자가 카테고리를 등록할 때 이름을 입력하지 않으면 예외가 발생한다.")
    @Test
    void addSubCategory() throws Exception {
        //given
        AddCategoryRequest request = AddCategoryRequest.builder()
                .name("")
                .level(1)
                .build();

        //when, then
        mockMvc.perform(post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"));

    }

}