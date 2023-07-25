package com.kkoch.admin.api.service.auction.dto;

import com.kkoch.admin.domain.auction.Status;
import lombok.Builder;
import lombok.Data;

@Data
public class SetAuctionStatusDto {

    private Long auctionId;
    private Status status;

    @Builder
    private SetAuctionStatusDto(Long auctionId, Status status) {
        this.auctionId = auctionId;
        this.status = status;
    }
}
