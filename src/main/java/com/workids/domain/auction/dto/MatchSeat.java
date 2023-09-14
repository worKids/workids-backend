package com.workids.domain.auction.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MatchSeat {
    private int seat;
    private Long userNum;
}
