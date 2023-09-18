package com.workids.domain.auction.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.workids.domain.auction.entity.Auction;
import com.workids.domain.auction.entity.AuctionNationStudent;
import com.workids.domain.nation.entity.NationStudent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseStudentAuctionListDto {

    private int seatNumber;

    private int resultSeat;

    private int submitPrice;

    private int AuctionState;

    private int resultType;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDateTime createdDate;

    public static ResponseStudentAuctionListDto toDto(Auction auction, AuctionNationStudent student) {
        return ResponseStudentAuctionListDto.builder()
                .seatNumber(student.getSubmitSeatNumber())
                .resultSeat(student.getResultSeatNumber())
                .submitPrice(student.getSubmitPrice())
                .AuctionState(auction.getAuctionState())
                .resultType(student.getResultType())
                .createdDate(auction.getCreatedDate())
                .build();
    }
}
