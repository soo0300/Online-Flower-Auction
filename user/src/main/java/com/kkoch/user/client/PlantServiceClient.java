package com.kkoch.user.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@FeignClient(name = "admin-service")
public interface PlantServiceClient {

    @GetMapping("/admin-service/plants/reservation")
    Long getPlantId(@SpringQueryMap Map<String, String> param);
}
