package com.kkoch.admin.domain.plant.repository.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class PlantSearchCond {

    String codeName;

    String type;

    String name;

    @Builder
    public PlantSearchCond(String codeName, String type, String name) {
        this.codeName = codeName;
        this.type = type;
        this.name = name;
    }
}
