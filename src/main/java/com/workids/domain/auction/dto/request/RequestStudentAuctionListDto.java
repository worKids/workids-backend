package com.workids.domain.auction.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RequestStudentAuctionListDto {
    private Long nationNum;

    private Long nationStudentNum;
}
