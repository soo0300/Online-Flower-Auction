package com.kkoch.admin.api.controller.category;

import com.kkoch.admin.ControllerTestSupport;
import com.kkoch.admin.api.controller.trade.TradeController;
import com.kkoch.admin.api.service.category.CategoryService;
import com.kkoch.admin.domain.plant.repository.CategoryRepository;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = {CategoryController.class})
class CategoryControllerTest extends ControllerTestSupport {

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private CategoryRepository categoryRepository;


}