package com.workids.domain.auction.service;

import com.workids.domain.auction.dto.request.RequestAuctionDto;
import com.workids.domain.auction.entity.Auction;
import com.workids.domain.auction.repository.AuctionRepository;
import com.workids.domain.nation.entity.Nation;
import com.workids.domain.nation.repository.NationRepository;

import com.workids.global.exception.ApiException;
import com.workids.global.exception.ExceptionEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuctionService {

    private final NationRepository nationRepository;
    private final AuctionRepository auctionRepository;
    @Transactional
    public void createAuction(RequestAuctionDto dto) {
        Nation nation = nationRepository.findById(dto.getNationNum())
                .orElseThrow(() -> new ApiException(ExceptionEnum.NATION_NOT_EXIST_EXCEPTION));
        if (!nation.getTeacher().getTeacherNum().equals(dto.getTeacherNum()))
            throw new ApiException(ExceptionEnum.TEACHER_NOT_MATCH_EXCEPTION);
        Auction auction = Auction.of(dto, nation);
        auctionRepository.save(auction);
    }
}
