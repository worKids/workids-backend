package com.workids.domain.auction.repository;

import com.workids.domain.auction.entity.AuctionNationStudent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuctionNationStudentRepository extends JpaRepository<AuctionNationStudent, Long> {
    List<AuctionNationStudent> findAllBySubmitSeatNumberAndAuction_AuctionNum(int seat, Long auctionNum);

    AuctionNationStudent findTopByAuction_AuctionNumAndSubmitSeatNumberOrderBySubmitPriceDescNationStudent_CreditRatingDescUpdatedDateDesc(Long auctionNum, int seat);
}
