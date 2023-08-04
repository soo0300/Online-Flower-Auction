package com.kkoch.admin.domain.plant.repository;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class StatsSearchCond {

    private Long plantId;

    private int searchDay;

    @Builder
    public StatsSearchCond(Long plantId, int searchDay) {
        this.plantId = plantId;
        this.searchDay = searchDay-1;
    }
}
