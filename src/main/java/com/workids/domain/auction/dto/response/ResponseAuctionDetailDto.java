package com.workids.domain.auction.dto.response;

import com.workids.domain.auction.entity.AuctionNationStudent;
import com.workids.domain.nation.entity.NationStudent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseAuctionDetailDto {
    private int citizenNum;

    private String name;

    private int seatNumber;

    private int resultPrice;

    public static ResponseAuctionDetailDto toDto(AuctionNationStudent ans, NationStudent ns) {
        return ResponseAuctionDetailDto.builder()
                .citizenNum(ns.getCitizenNumber())
                .name(ns.getStudentName())
                .seatNumber(ans.getResultSeatNumber())
                .resultPrice(ans.getResultPrice())
                .build();
    }
}
