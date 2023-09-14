package com.workids.domain.auction.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestStudentAuctionDto {
    private Long auctionNum;
    private Long nationStudentNum;
    private int submitSeat;
    private int submitPrice;
}
