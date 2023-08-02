package com.kkoch.admin.api.controller.plant.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PlantResponse {

   private Long plantId;

   private String code;

   private String type;

   private String name;

    @Builder
    public PlantResponse(Long plantId, String code, String type, String name) {
        this.plantId = plantId;
        this.code = code;
        this.type = type;
        this.name = name;
    }
}
