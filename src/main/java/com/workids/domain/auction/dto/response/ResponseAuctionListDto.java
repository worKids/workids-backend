package com.workids.domain.auction.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.workids.domain.auction.entity.Auction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseAuctionListDto {
    private Long auctionNum;

    private int auctionState;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdDate;


    public static ResponseAuctionListDto toDto(Auction auction) {
        return ResponseAuctionListDto.builder()
                .auctionNum(auction.getAuctionNum())
                .createdDate(auction.getCreatedDate())
                .auctionState(auction.getAuctionState())
                .build();
    }
}
