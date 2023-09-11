package com.workids.domain.auction.entity;

import com.workids.domain.auction.dto.request.RequestAuctionDto;
import com.workids.domain.nation.entity.Nation;
import com.workids.global.config.TimeEntity;
import com.workids.global.config.stateType.AuctionStateType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Auction extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auction_seq")
    @SequenceGenerator(name="auction_seq", sequenceName = "auction_seq", allocationSize = 1)
    private Long auctionNum;

    @ManyToOne(targetEntity = Nation.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "nation_num")
    private Nation nation;

    @Column(nullable = false)
    private int classRow;

    @Column(nullable = false)
    private int classColumn;

    @Column(nullable = false)
    private int totalSeat;

    @Column(nullable = false)
    private int auctionState;

    @Column(nullable = true)
    private LocalDate endDate;

    public static Auction of(RequestAuctionDto dto, Nation nation) {
        return Auction.builder()
                .nation(nation)
                .classRow(dto.getRow())
                .classColumn(dto.getColumn())
                .totalSeat(dto.getTotalSeat())
                .auctionState(AuctionStateType.IN_PROGRESS)

                .build();
    }
}
