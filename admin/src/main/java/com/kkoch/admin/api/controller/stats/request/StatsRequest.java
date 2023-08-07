package com.kkoch.admin.api.controller.stats.request;

import com.kkoch.admin.domain.plant.repository.StatsSearchCond;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class StatsRequest {

    private String type;

    private String name;

    private int searchDay;

    @Builder
    private StatsRequest(String type, String name, int searchDay) {
        this.type = type;
        this.name = name;
        this.searchDay = searchDay;
    }

    @Builder
    public StatsSearchCond toStatsSearchCond() {
        return StatsSearchCond.builder()
                .type(this.type)
                .name(this.name)
                .searchDay(this.searchDay)
                .build();
    }
}
