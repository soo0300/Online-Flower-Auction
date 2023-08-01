package com.kkoch.admin.api.service.plant;

import com.kkoch.admin.domain.plant.repository.PlantQueryRepository;
import com.kkoch.admin.domain.plant.repository.dto.PlantSearchCond;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class PlantQueryService {

    public List<PlantQueryRepository> getPlants(PlantSearchCond plantSearchCond) {
        return null;
    }
}
