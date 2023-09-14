package com.workids.domain.auction.entity;

import com.workids.domain.auction.dto.request.RequestAuctionDto;
import com.workids.domain.auction.dto.request.RequestStudentAuctionDto;
import com.workids.domain.auction.entity.Auction;
import com.workids.domain.nation.entity.NationStudent;
import com.workids.global.config.BaseTimeEntity;
import com.workids.global.config.TimeEntity;
import com.workids.global.config.stateType.AuctionStateType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuctionNationStudent extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auction_nation_student_seq")
    @SequenceGenerator(name = "auction_nation_student_seq", sequenceName = "auction_nation_student_seq", allocationSize = 1)
    private Long auctionNationStudentNum;

    @ManyToOne(targetEntity = Auction.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "auction_num")
    private Auction auction;

    // onetoone 도 괜찮을 거 같음
    @ManyToOne(targetEntity = NationStudent.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "nation_student_num")
    private NationStudent nationStudent;

    private int submitSeatNumber;

    private int submitPrice;

    private int resultSeatNumber;

    private int resultPrice;

    @Column(nullable = false)
    private int resultType;

    public static AuctionNationStudent of(Auction auction,
                                          NationStudent nationStudent) {

        return AuctionNationStudent.builder()
                .auction(auction)
                .nationStudent(nationStudent)
                .submitSeatNumber(0)
                .submitPrice(0)
                .resultSeatNumber(0)
                .resultPrice(0)
                .resultType(AuctionStateType.NOT_SUBMITTER)
                .build();
    }
}
