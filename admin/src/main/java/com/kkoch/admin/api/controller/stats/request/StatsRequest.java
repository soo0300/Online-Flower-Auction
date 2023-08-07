package com.kkoch.admin.api.controller.stats.request;

import com.kkoch.admin.domain.plant.repository.StatsSearchCond;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class StatsRequest {

    private String name;

    private String type;

    private int searchDay;

    @Builder
    private StatsRequest(String name, String type, int searchDay) {
        this.name = name;
        this.type = type;
        this.searchDay = searchDay;
    }

    public StatsSearchCond toStatsSearchCond() {
        return StatsSearchCond.builder()
                .name(this.name)
                .type(this.type)
                .searchDay(this.searchDay)
                .build();
    }
}
