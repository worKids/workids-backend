package com.workids.domain.auction.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestAuctionDto {
    private Long nationNum;

    private Long teacherNum;

    private int row;

    private int column;

    private int totalSeat;

    private int state;
}
