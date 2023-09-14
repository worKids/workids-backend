package com.workids.domain.auction.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.workids.domain.auction.dto.request.RequestStudentAuctionDto;
import com.workids.domain.auction.entity.Auction;
import com.workids.domain.auction.entity.QAuctionNationStudent;
import com.workids.domain.auction.repository.AuctionNationStudentRepository;
import com.workids.domain.auction.repository.AuctionRepository;
import com.workids.domain.nation.entity.NationStudent;
import com.workids.domain.nation.repository.NationRepository;
import com.workids.domain.nation.repository.NationStudentRepository;
import com.workids.global.config.stateType.AuctionStateType;
import com.workids.global.exception.ApiException;
import com.workids.global.exception.ExceptionEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StudentAuctionService {
    private final JPAQueryFactory queryFactory;

    private final NationRepository nationRepository;
    private final AuctionRepository auctionRepository;
    private final AuctionNationStudentRepository auctionNationStudentRepository;
    private final NationStudentRepository nationStudentRepository;

    /**
     * 경매 입찰(학생)
     * 주거래 잔액과 입찰 금액 비교, 입찰 금액과 최고 금액 비교
     */
    public void bidSeat(RequestStudentAuctionDto dto) {
        // 주거래 잔액 비교 결과 추가하기

        // 옥션 가져오고 없으면 에러
        Auction auction = auctionRepository.findByAuctionNum(dto.getAuctionNum());
        if (auction == null) {
            throw new ApiException(ExceptionEnum.AUCTION_NOT_EXIST_EXCEPTION);
        }

        // 만약 종료된 옥션일 경우 에러
        if (auction.getAuctionState() == AuctionStateType.CLOSE ||
                auction.getAuctionState() == AuctionStateType.DELETE) {
            throw new ApiException(ExceptionEnum.AUCTION_NOT_EXIST_EXCEPTION);
        }
        // 입력한 자리가 옥션 자리에 없으면 에러
        if (auction.getTotalSeat() < dto.getSubmitSeat() || dto.getSubmitSeat() <= 0) {
            throw new ApiException(ExceptionEnum.AUCTION_SEAT_NOT_EXIST_EXCEPTION);
        }

        // 나라학생 가져오고 없으면 에러
        NationStudent nationStudent = nationStudentRepository.findByNationStudentNum(dto.getNationStudentNum());
        if (nationStudent == null) {
            throw new ApiException(ExceptionEnum.NATION_STUDENT_NOT_EXIST_EXCEPTION);
        }

        // 업데이트 하기
        updateSeat(dto);

        // 경매에서 내가 신청한 자리를 신청한 사람이 있는지 체크하고, 없으면 생성.
//        AuctionNationStudent student = auctionNationStudentRepository
//                .findByResultSeatNumberAndAuction_AuctionNum(dto.getSubmitSeat(), dto.getAuctionNum());
//        if (student == null) {
//            updateSeat(auctionNationStudent, dto);
//        } else if (dto.getSubmitPrice() == student.getResultPrice()) {
//            if (nationStudent.getCreditRating() > student.getNationStudent().getCreditRating()) {
//                // 신용도가 높은 애껄로 적용
//            }
//        }
    }

    // querydsl용 함수

    /**
     * 학생 auction 등록 update문
     * @param dto
     */
    private void updateSeat(RequestStudentAuctionDto dto) {
        QAuctionNationStudent auctionNationStudent = QAuctionNationStudent.auctionNationStudent;
        queryFactory.update(auctionNationStudent)
                .set(auctionNationStudent.submitSeatNumber, dto.getSubmitSeat())
                .set(auctionNationStudent.submitPrice, dto.getSubmitPrice())
                .where(auctionNationStudent.auction.auctionNum.eq(dto.getAuctionNum())
                        .and(auctionNationStudent.nationStudent.nationStudentNum.eq(dto.getNationStudentNum())))
                .execute();
    }
}
