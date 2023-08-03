package com.kkoch.admin.api.service.stats;

import com.kkoch.admin.api.service.stats.dto.AuctionArticleForStatsDto;
import com.kkoch.admin.domain.plant.repository.StatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RequiredArgsConstructor
@Transactional
@Service
public class StatsService {

    private final StatsRepository statsRepository;

    public Long addStats(List<AuctionArticleForStatsDto> dto) {
        Map<Long, List<AuctionArticleForStatsDto>> plantDataMap = new HashMap<>();

        // 식물별로 경매품 저장
        for (AuctionArticleForStatsDto data : dto) {
            Long plantId = data.getPlantId();

            List<AuctionArticleForStatsDto> plantDataList = plantDataMap.getOrDefault(plantId, new ArrayList<>());
            plantDataList.add(data);
            plantDataMap.put(plantId, plantDataList);
        }
        // 식물의 등급별로 평균을 구한다
        Set<Long> keySet = plantDataMap.keySet();

//        for(Long key : keySet){
//
//        }
        // 이떄. 최고값, 최저값을 넣는다
        return 1L;
    }
}
