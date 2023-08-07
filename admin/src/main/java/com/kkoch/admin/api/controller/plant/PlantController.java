package com.kkoch.admin.api.controller.plant;

import com.kkoch.admin.api.ApiResponse;
import com.kkoch.admin.api.controller.plant.request.GetPlantRequest;
import com.kkoch.admin.api.controller.plant.response.PlantResponse;
import com.kkoch.admin.api.service.plant.PlantQueryService;
import com.kkoch.admin.api.service.plant.dto.PlantSearchCond;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/reservation")
    public Long getPlantId(
        @RequestParam String type,
        @RequestParam String name
    ) {
        return plantQueryService.getPlantId(type, name);
    }
}
