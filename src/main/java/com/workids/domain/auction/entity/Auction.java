package com.workids.domain.auction.entity;

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
    private int row;

    @Column(nullable = false)
    private int column;

    @Column(nullable = false)
    private int state;

    @Column(nullable = true)
    private LocalDate endDate;

    public static Auction of(Auction auction) {
        return Auction.builder()
                .nation(auction.getNation())
                .row(auction.getRow())
                .column(auction.getColumn())
                .state(AuctionStateType.IN_PROGRESS)
                .build();
    }
}
