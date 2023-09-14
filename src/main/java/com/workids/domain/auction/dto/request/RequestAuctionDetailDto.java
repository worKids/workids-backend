package com.workids.domain.auction.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestAuctionDetailDto {
    private Long auctionNum;
    private int auctionState;
}
