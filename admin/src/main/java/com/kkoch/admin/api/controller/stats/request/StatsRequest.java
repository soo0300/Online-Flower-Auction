package com.kkoch.admin.api.controller.stats.request;

import com.kkoch.admin.domain.plant.repository.StatsSearchCond;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class StatsRequest {

    private Long plantId;

    private int searchDay;

    @Builder
    private StatsRequest(Long plantId, int searchDay) {
        this.plantId = plantId;
        this.searchDay = searchDay;
    }

    public StatsSearchCond toStatsSearchCond() {
        return StatsSearchCond.builder()
                .plantId(this.plantId)
                .searchDay(this.searchDay)
                .build();
    }
}
