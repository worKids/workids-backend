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
     * 경매 생성(선생님)
     * @param dto
     */
    @Transactional
    public void createAuction(RequestAuctionDto dto) {
        // 나라 여부 체크
        Nation nation = nationRepository.findByNationNum(dto.getNationNum())
                .orElseThrow(() -> new ApiException(ExceptionEnum.NATION_NOT_EXIST_EXCEPTION));
        // 선생이 맞는지 체크
        if (!nation.getTeacher().getTeacherNum().equals(dto.getTeacherNum()))
            throw new ApiException(ExceptionEnum.TEACHER_NOT_MATCH_EXCEPTION);

        // 경매 저장
        Auction auction = Auction.of(dto, nation);
        Auction newAuction = auctionRepository.save(auction);

        // 경매 저장 시 학생 경매 베이스 생성
        List<NationStudent> list = nationStudentRepository.findAllByNation_NationNum(dto.getNationNum());
        if (list.isEmpty()) {
            throw new ApiException(ExceptionEnum.NATION_STUDENT_NOT_EXIST_EXCEPTION);
        }
        for (NationStudent student : list) {
            auctionNationStudentRepository.save(AuctionNationStudent.of(newAuction, student));
        }
    }

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
        // 입력한 자리가 옥션 자리에 없으면 에러
        if (auction.getTotalSeat() < dto.getSubmitSeat() || dto.getSubmitSeat() <= 0) {
            throw new ApiException(ExceptionEnum.AUCTION_SEAT_NOT_EXIST);
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
        System.out.println("함수 입장");
        queryFactory.update(auctionNationStudent)
                .set(auctionNationStudent.submitSeatNumber, dto.getSubmitSeat())
                .set(auctionNationStudent.submitPrice, dto.getSubmitPrice())
                .where(auctionNationStudent.auction.auctionNum.eq(dto.getAuctionNum())
                        .and(auctionNationStudent.nationStudent.nationStudentNum.eq(dto.getNationStudentNum())))
                .execute();
        System.out.println("함수 끝");
    }

}
