package com.kkoch.admin.api.controller.plant;

import com.kkoch.admin.api.ApiResponse;
import com.kkoch.admin.api.controller.plant.request.GetPlantRequest;
import com.kkoch.admin.api.controller.plant.response.PlantResponse;
import com.kkoch.admin.api.service.plant.PlantQueryService;
import com.kkoch.admin.api.service.plant.dto.PlantSearchCond;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/admin-service/plants")
@RestController
public class PlantController {

    private final PlantQueryService plantQueryService;

    @GetMapping
    public ApiResponse<List<PlantResponse>> getPlants(@RequestBody GetPlantRequest request) {
        PlantSearchCond cond = request.toSearchCond();
        return ApiResponse.ok(plantQueryService.getPlants(cond));
    }
}
