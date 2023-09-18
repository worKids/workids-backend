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
public class ResponseStudentAuctionDto {
    private int classRow;
    private int classColumn;
    private int totalSeat;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDateTime createdDate;
    public static ResponseStudentAuctionDto toDto(Auction auction) {
        return ResponseStudentAuctionDto.builder()
                .classRow(auction.getClassRow())
                .classColumn(auction.getClassColumn())
                .totalSeat(auction.getTotalSeat())
                .createdDate(auction.getCreatedDate())
                .build();
    }
}
