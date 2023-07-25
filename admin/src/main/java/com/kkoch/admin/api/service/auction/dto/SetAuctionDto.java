package com.kkoch.admin.api.service.auction.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SetAuctionDto {

    private Long auctionId;
    private LocalDateTime startTime;
    private int code;

    @Builder
    public SetAuctionDto(Long auctionId, LocalDateTime startTime, int code) {
        this.auctionId = auctionId;
        this.startTime = startTime;
        this.code = code;
    }
}
