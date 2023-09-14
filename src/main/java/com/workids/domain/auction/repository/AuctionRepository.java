package com.workids.domain.auction.repository;

import com.workids.domain.auction.entity.Auction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface AuctionRepository extends JpaRepository<Auction, Long> {
    List<Auction> findAllByNation_NationNumOrderByCreatedDate(Long nationNum);

    Auction findByAuctionNum(Long auctionNum);
}
