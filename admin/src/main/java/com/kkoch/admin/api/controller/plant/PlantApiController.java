package com.kkoch.admin.api.controller.plant;

import com.kkoch.admin.api.ApiResponse;
import com.kkoch.admin.api.controller.plant.request.GetPlantRequest;
import com.kkoch.admin.api.controller.plant.response.PlantResponse;
import com.kkoch.admin.api.service.plant.PlantQueryService;
import com.kkoch.admin.api.service.plant.PlantService;
import com.kkoch.admin.api.service.plant.dto.PlantSearchCond;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/admin-service/plants")
@RestController
public class PlantApiController {

    private final PlantQueryService plantQueryService;

    private final PlantService plantService;

    @GetMapping("/reservation")
    public Long getPlantId(
            @RequestParam String type,
            @RequestParam String name
    ) {
        return plantQueryService.getPlantId(type, name);
    }
}