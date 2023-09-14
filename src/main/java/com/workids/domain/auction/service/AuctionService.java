package com.workids.domain.auction.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.workids.domain.auction.dto.request.RequestAuctionDto;
import com.workids.domain.auction.dto.request.RequestAuctionListDto;
import com.workids.domain.auction.dto.request.RequestStudentAuctionDto;
import com.workids.domain.auction.dto.response.ResponseAuctionListDto;
import com.workids.domain.auction.entity.Auction;
import com.workids.domain.auction.entity.AuctionNationStudent;
import com.workids.domain.auction.entity.QAuctionNationStudent;
import com.workids.domain.auction.repository.AuctionNationStudentRepository;
import com.workids.domain.auction.repository.AuctionRepository;
import com.workids.domain.nation.entity.Nation;
import com.workids.domain.nation.entity.NationStudent;
import com.workids.domain.nation.repository.NationRepository;
import com.workids.domain.nation.repository.NationStudentRepository;
import com.workids.global.config.stateType.AuctionStateType;
import com.workids.global.exception.ApiException;
import com.workids.global.exception.ExceptionEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import static java.util.stream.Collectors.toList;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuctionService {
    private final JPAQueryFactory queryFactory;

    private final NationRepository nationRepository;
    private final AuctionRepository auctionRepository;
    private final AuctionNationStudentRepository auctionNationStudentRepository;
    private final NationStudentRepository nationStudentRepository;

    /**
     * 경매 조회(모두)
     * @param dto
     * @return
     */
    public List<ResponseAuctionListDto> getAuctionList(RequestAuctionListDto dto) {
        List<Auction> list = auctionRepository.findAllByNation_NationNumOrderByCreatedDate(dto.getNationNum());
        if (list.isEmpty()) return null;
        List<ResponseAuctionListDto> result = list.stream().map(
                auction ->  ResponseAuctionListDto.toDto(auction))
                .collect(toList());
        return result;
    }



}
