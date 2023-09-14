package com.workids.domain.auction.repository;

import com.workids.domain.auction.entity.AuctionNationStudent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuctionNationStudentRepository extends JpaRepository<AuctionNationStudent, Long> {
    AuctionNationStudent findByResultSeatNumberAndAuction_AuctionNum(int seat, Long auctionNum);

}
