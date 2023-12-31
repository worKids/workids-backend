package com.workids.domain.auction.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.workids.domain.auction.dto.request.RequestAuctionDoneDto;
import com.workids.domain.auction.dto.request.RequestAuctionListDto;
import com.workids.domain.auction.dto.request.RequestStudentAuctionDto;
import com.workids.domain.auction.dto.request.RequestStudentAuctionListDto;
import com.workids.domain.auction.dto.response.ResponseStudentAuctionDto;
import com.workids.domain.auction.dto.response.ResponseStudentAuctionListDto;
import com.workids.domain.auction.entity.Auction;
import com.workids.domain.auction.entity.AuctionNationStudent;
import com.workids.domain.auction.entity.QAuction;
import com.workids.domain.auction.entity.QAuctionNationStudent;
import com.workids.domain.auction.repository.AuctionNationStudentRepository;
import com.workids.domain.auction.repository.AuctionRepository;
import com.workids.domain.bank.entity.BankNationStudent;
import com.workids.domain.bank.entity.QBankNationStudent;
import com.workids.domain.bank.repository.BankNationStudentRepository;
import com.workids.domain.nation.entity.NationStudent;
import com.workids.domain.nation.repository.NationRepository;
import com.workids.domain.nation.repository.NationStudentRepository;
import com.workids.global.config.stateType.AuctionStateType;
import com.workids.global.config.stateType.BankStateType;
import com.workids.global.exception.ApiException;
import com.workids.global.exception.ExceptionEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StudentAuctionService {
    private final JPAQueryFactory queryFactory;

    private final NationRepository nationRepository;
    private final AuctionRepository auctionRepository;
    private final AuctionNationStudentRepository auctionNationStudentRepository;
    private final NationStudentRepository nationStudentRepository;
    private final BankNationStudentRepository bankNationStudentRepository;
    /**
     * 경매 입찰(학생)
     * 주거래 잔액과 입찰 금액 비교, 입찰 금액과 최고 금액 비교
     */
    @Transactional
    public void bidSeat(RequestStudentAuctionDto dto) {
        // 주거래 잔액 비교 결과 추가하기
        Long balance = getBalance(dto.getNationStudentNum());
        System.out.println("balance = " + balance);
        if (balance < dto.getSubmitPrice()) {
            throw new ApiException(ExceptionEnum.AUCTION_NOT_ENOUGH_AMOUNT_EXCEPTION);
        }
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
    }

    /**
     * 최근 경매 조회
     * @param dto
     * @return
     */
    public ResponseStudentAuctionDto getAuction(RequestAuctionListDto dto) {
        Auction auction = getRecentAuction(dto.getNationNum());
        if (auction == null) {
            return null;
        }
        return ResponseStudentAuctionDto.toDto(auction);
    }

    /**
     * 학생 별 경매 입찰 내역
     * @param dto
     * @return
     */
    public List<ResponseStudentAuctionListDto> getAuctionList(RequestStudentAuctionListDto dto) {
        List<Auction> auctions = getAuctions(dto.getNationNum());
        if (auctions.isEmpty()) {
            return null;
        }
        List<ResponseStudentAuctionListDto> list = auctions.stream().map(auction -> {
            System.out.println("auction.getAuctionNum() = " + auction.getAuctionNum());
            System.out.println("dto.getNationStudentNum() = " + dto.getNationStudentNum());
            AuctionNationStudent student = getStudent(auction.getAuctionNum(), dto.getNationStudentNum());
            return ResponseStudentAuctionListDto.toDto(auction, student);
        }).collect(Collectors.toList());
        return list;
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

    /**
     * 은행 잔액 가져오기
     * @param studentNum
     * @return
     */
    private Long getBalance(Long studentNum) {
        QBankNationStudent account = QBankNationStudent.bankNationStudent;
        System.out.println("studentNum = " + studentNum);
        Long balance = queryFactory.select(account.balance)
                .from(account)
                .where(account.bank.productType.eq(BankStateType.MAIN_ACCOUNT)
                        .and(account.nationStudent.nationStudentNum.eq(studentNum)))
                .fetchOne();
        System.out.println("getbalance 종료");
        return balance;
    }
    private Auction getRecentAuction(Long nationNum) {
        QAuction auction = QAuction.auction;
        Auction a = queryFactory.selectFrom(auction)
                .where(auction.nation.nationNum.eq(nationNum)
                        .and(auction.auctionState.eq(AuctionStateType.IN_PROGRESS)))
                .orderBy(auction.createdDate.desc())
                .limit(1)
                .fetchOne();
        return a;
    }

    /**
     * 취소되지 않은 경매 내역
     * @param nationNum
     * @return
     */
    private List<Auction> getAuctions(Long nationNum) {
        QAuction auction = QAuction.auction;
        List<Auction> list = queryFactory.selectFrom(auction)
                .where(auction.nation.nationNum.eq(nationNum)
                        .and(auction.auctionState.ne(AuctionStateType.DELETE)))
                .orderBy(auction.createdDate.asc())
                .fetch();
        return list;
    }

    /**
     * 경매별 학생 조회
     * @param auctionNum
     * @param studentNum
     * @return
     */
    private AuctionNationStudent getStudent(Long auctionNum, Long studentNum) {
        QAuctionNationStudent student = QAuctionNationStudent.auctionNationStudent;
        AuctionNationStudent a = queryFactory.selectFrom(student)
                .where(student.auction.auctionNum.eq(auctionNum)
                        .and(student.nationStudent.nationStudentNum.eq(studentNum)))
                .fetchOne();
        return a;
    }
}
