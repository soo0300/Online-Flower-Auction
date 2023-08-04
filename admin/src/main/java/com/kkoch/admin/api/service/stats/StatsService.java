package com.kkoch.admin.api.service.stats;

import com.kkoch.admin.api.service.stats.dto.AuctionArticleForStatsDto;
import com.kkoch.admin.domain.Grade;
import com.kkoch.admin.domain.plant.Stats;
import com.kkoch.admin.domain.plant.repository.PlantRepository;
import com.kkoch.admin.domain.plant.repository.StatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Transactional
@Service
public class StatsService {

    private final StatsRepository statsRepository;

    private final PlantRepository plantRepository;

    public void addStats(List<AuctionArticleForStatsDto> dto) {
        Map<Long, List<AuctionArticleForStatsDto>> plantDataMap = new HashMap<>();
        for (AuctionArticleForStatsDto data : dto) {
            Long plantId = data.getPlantId();
            List<AuctionArticleForStatsDto> plantList = plantDataMap.getOrDefault(plantId, new ArrayList<>());
            plantList.add(data);
            plantDataMap.put(plantId, plantList);
        }

        // 각 식물 당 등급별로 평균, 최고, 최저 낙찰금액 계산
        for (Long plantId : plantDataMap.keySet()) {
            List<AuctionArticleForStatsDto> plantList = plantDataMap.get(plantId);

            // 각 등급별 데이터를 그룹화하여 맵에 저장
            Map<Grade, List<AuctionArticleForStatsDto>> gradeDataMap = new HashMap<>();
            for (AuctionArticleForStatsDto data : plantList) {
                Grade grade = data.getGrade();
                List<AuctionArticleForStatsDto> gradeList = gradeDataMap.getOrDefault(grade, new ArrayList<>());
                gradeList.add(data);
                gradeDataMap.put(grade, gradeList);
            }

            for (Grade grade : gradeDataMap.keySet()) {
                List<AuctionArticleForStatsDto> gradeList = gradeDataMap.get(grade);
                int sum = 0;
                int totalGenus =0;
                int count = 0;
                int maxBidPrice = Integer.MIN_VALUE;
                int minBidPrice = Integer.MAX_VALUE;

                for (AuctionArticleForStatsDto data : gradeList) {
                    int bidPrice = data.getBidPrice();
                    int bidGenus = data.getCount();
                    sum += bidPrice;
                    totalGenus += bidGenus;
                    count++;
                    maxBidPrice = Math.max(maxBidPrice, bidPrice);
                    minBidPrice = Math.min(minBidPrice, bidPrice);
                }
                int avgBidPrice = sum / count;
                Stats stats = Stats.builder()
                        .priceAvg(avgBidPrice)
                        .priceMax(maxBidPrice)
                        .priceMin(minBidPrice)
                        .grade(grade)
                        .count(totalGenus)
                        .plant(plantRepository.findById(plantId).get())
                        .build();

                statsRepository.save(stats);
            }
        }
    }

}
