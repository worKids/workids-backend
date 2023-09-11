package com.workids.domain.auction.repository;

import com.workids.domain.auction.entity.Auction;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AuctionRepository extends JpaRepository<Auction, Long> {
}
