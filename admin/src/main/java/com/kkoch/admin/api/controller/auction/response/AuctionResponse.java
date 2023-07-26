package com.kkoch.admin.api.controller.auction.response;

import com.kkoch.admin.domain.auction.Status;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AuctionResponse {

    private Long auctionId;
    private int code;
    private Status status;
    private LocalDateTime startTime;
    private LocalDateTime createdDate;

    @Builder
    public AuctionResponse(Long auctionId, int code, Status status, LocalDateTime startTime, LocalDateTime createdDate) {
        this.auctionId = auctionId;
        this.code = code;
        this.status = status;
        this.startTime = startTime;
        this.createdDate = createdDate;
    }
}
