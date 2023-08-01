package com.kkoch.admin.api.controller.plant.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class GetPlantRequest {

    String code;

    String type;

    String name;

    @Builder
    public GetPlantRequest(String code, String type, String name) {
        this.code = code;
        this.type = type;
        this.name = name;
    }


}
