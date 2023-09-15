package com.workids.domain.auction.service;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.workids.domain.auction.dto.MatchSeat;
import com.workids.domain.auction.dto.request.RequestAuctionDetailDto;
import com.workids.domain.auction.dto.request.RequestAuctionDoneDto;
import com.workids.domain.auction.dto.request.RequestAuctionDto;
import com.workids.domain.auction.dto.response.ResponseAuctionDetailDto;
import com.workids.domain.auction.entity.Auction;
import com.workids.domain.auction.entity.AuctionNationStudent;
import com.workids.domain.auction.entity.QAuctionNationStudent;
import com.workids.domain.auction.repository.AuctionNationStudentRepository;
import com.workids.domain.auction.repository.AuctionRepository;
import com.workids.domain.bank.entity.BankNationStudent;
import com.workids.domain.bank.entity.QBankNationStudent;
import com.workids.domain.bank.entity.TransactionHistory;
import com.workids.domain.bank.repository.BankNationStudentRepository;
import com.workids.domain.bank.repository.TransactionHistoryRepository;
import com.workids.domain.nation.entity.Nation;
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

import javax.persistence.criteria.Subquery;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.workids.domain.auction.entity.QAuction.auction;
import static java.util.stream.Collectors.toList;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeacherAuctionService {

    private final NationRepository nationRepository;
    private final AuctionRepository auctionRepository;
    private final AuctionNationStudentRepository auctionNationStudentRepository;
    private final NationStudentRepository nationStudentRepository;
    private final TransactionHistoryRepository transactionHistoryRepository;
    private final JPAQueryFactory queryFactory;
    private final BankNationStudentRepository bankNationStudentRepository;
    /**
     * 경매 생성
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
     * 경매 종료
     * @param dto
     */
    @Transactional
    public void auctionDone(RequestAuctionDoneDto dto) {
        Auction auction = auctionRepository.findByAuctionNum(dto.getAuctionNum());
        // 경매 없을 때 에러
        if (auction==null) {
            throw new ApiException(ExceptionEnum.AUCTION_NOT_EXIST_EXCEPTION);
        }
        // 경매가 진행중인 것만 수정 추가
        if (auction.getAuctionState()==AuctionStateType.CLOSE
                || auction.getAuctionState()==AuctionStateType.DELETE) {
            throw new ApiException(ExceptionEnum.AUCTION_NOT_CONTINUE_EXCEPTION);
        }

        updateState(dto.getAuctionNum(), AuctionStateType.CLOSE);
        List<Integer> seats = new ArrayList<>();
        for (int i = 1; i<=auction.getTotalSeat();i++) {
            List<AuctionNationStudent> candidate = getSameSeatList(dto.getAuctionNum(), i);
            if (candidate.isEmpty()) {
                seats.add(i);
                continue;
            }

            selectWinStudent(dto.getAuctionNum(), i);
        }
        List<Long> noSeat = getNoSeatList(dto.getAuctionNum());
        Collections.shuffle(noSeat);
        List<MatchSeat> matchResult = new ArrayList<>();

        for (int i = 0; i< seats.size(); i++) {
            int seat = seats.get(i);
            Long userNum = noSeat.get(i);
            matchResult.add(new MatchSeat(seat, userNum));
        }
        for (MatchSeat seat : matchResult) {
            updateLoseResult(seat.getUserNum(), seat.getSeat());
        }
    }

    /**
     * 경매 디테일 조회
     * @param dto
     * @return
     */
    public List<ResponseAuctionDetailDto> getDetail(RequestAuctionDetailDto dto) {
        Auction auction = auctionRepository.findByAuctionNum(dto.getAuctionNum());
        // 경매 없을 때 에러
        if (auction==null) {
            throw new ApiException(ExceptionEnum.AUCTION_NOT_EXIST_EXCEPTION);
        }
        // 0일 때 => 미참여, 1 => 성공, 2 => 실패 3=> 전체 조회
        if (dto.getAuctionState() == 3) {
            List<AuctionNationStudent> list = auctionNationStudentRepository
                    .findAllByAuction_AuctionNum(dto.getAuctionNum());
            return list.stream().map(a ->
                    ResponseAuctionDetailDto.toDto(a, a.getNationStudent())).collect(toList());
        } else {
            List<AuctionNationStudent> list = auctionNationStudentRepository
                    .findAllByAuction_AuctionNumAndAndResultType(dto.getAuctionNum(), dto.getAuctionState());

            return list.stream().map(a ->
                    ResponseAuctionDetailDto.toDto(a, a.getNationStudent())).collect(toList());
        }
    }

    /**
     * 경매 삭제
     * @param dto
     */
    public void deleteAuction(RequestAuctionDoneDto dto) {
        Auction auction = auctionRepository.findByAuctionNum(dto.getAuctionNum());
        // 경매 없을 때 에러
        if (auction==null) {
            throw new ApiException(ExceptionEnum.AUCTION_NOT_EXIST_EXCEPTION);
        }
        updateState(dto.getAuctionNum(), AuctionStateType.DELETE);

        // 학생들 데이터 삭제
        List<AuctionNationStudent> list = auctionNationStudentRepository
                .findAllByAuction_AuctionNum(dto.getAuctionNum());

        for (AuctionNationStudent student : list) {
            deleteBid(student.getAuctionNationStudentNum());
        }
    }

    /**
     * querydsl 함수들
     */

    /**
     * 경매 상태 변경
     * @param auctionNum
     * @param state
     */
    private void updateState(Long auctionNum, int state) {
        queryFactory.update(auction)
                .set(auction.auctionState, state)
                .where(auction.auctionNum.eq(auctionNum))
                .execute();
    }

    /**
     * 경매 종료 시 입찰 결과(승)
     * @param studentNum
     * @param price
     * @param seatNum
     */
    private void updateWinResult(Long studentNum, int price, int seatNum) {
        QAuctionNationStudent student = QAuctionNationStudent.auctionNationStudent;
        queryFactory.update(student)
                .set(student.resultPrice, price)
                .set(student.resultSeatNumber, seatNum)
                .set(student.resultType, AuctionStateType.SUCCESSFUL_BIDDER)
                .where(student.auctionNationStudentNum.eq(studentNum))
                .execute();
        System.out.println("업데이트 경매 결과");
        // 주거래 통장에서 낙찰 금액만큼 이체

        Long stNum = queryFactory.select(student.nationStudent.nationStudentNum)
                .from(student)
                .where(student.auctionNationStudentNum.eq(studentNum))
                .fetchOne();
        System.out.println("stNum = " + stNum);

        BankNationStudent account =  bankNationStudentRepository
                .findByNationStudent_NationStudentNumAndBank_ProductType(stNum, BankStateType.MAIN_ACCOUNT);
        account.updateBalance(account.getBalance() - price);

        System.out.println("주거래 변경");
        QBankNationStudent qBankNationStudent = QBankNationStudent.bankNationStudent;
        BankNationStudent bankNationStudent = queryFactory.selectFrom(qBankNationStudent)
                .where(qBankNationStudent.nationStudent.nationStudentNum
                        .eq(JPAExpressions.select(student.nationStudent.nationStudentNum)
                                .from(student)
                                .where(student.auctionNationStudentNum.eq(studentNum)))
                        .and(qBankNationStudent.bank.productType.eq(BankStateType.MAIN_ACCOUNT)))
                .fetchOne();

        // 예금 통장 transaction 생성
        TransactionHistory newTransactionHistory = TransactionHistory.of(bankNationStudent, "부동산 경매 낙찰", BankStateType.CATEGORY_AUCTION, BankStateType.WITHDRAW, (long) price);
        transactionHistoryRepository.save(newTransactionHistory);

    }

    /**
     * 경매 종료 시 입찰 결과(패)
     * @param studentNum
     */
    private void updateLoseResult(Long studentNum, int seat) {
        QAuctionNationStudent student = QAuctionNationStudent.auctionNationStudent;
        int checking = checkBid(studentNum);
        System.out.println("checking = " + checking);
        if (checking == AuctionStateType.NOT_SUBMITTER) {
            queryFactory.update(student)
                    .set(student.resultSeatNumber, seat)
                    .where(student.auctionNationStudentNum.eq(studentNum))
                    .execute();
        } else {
            queryFactory.update(student)
                    .set(student.resultType, AuctionStateType.NON_SUCCESSFUL_BIDDER)
                    .set(student.resultSeatNumber, seat)
                    .where(student.auctionNationStudentNum.eq(studentNum))
                    .execute();
        }
    }

    /**
     * 경매 참여 여부 체크
     * @param studentNum
     * @return
     */
    private int checkBid(Long studentNum) {
        QAuctionNationStudent student = QAuctionNationStudent.auctionNationStudent;

        int result = queryFactory.select(student.submitSeatNumber)
                .from(student)
                .where(student.auctionNationStudentNum.eq(studentNum))
                .fetchOne();
        return result;
    }


    /**
     * 학생 경매 결과 생성.
     * @param auctionNum
     * @param seatNum
     * @return
     */
    private void selectWinStudent(Long auctionNum, int seatNum) {
        System.out.println("이겼을 경우");
        QAuctionNationStudent auctionNationStudent = QAuctionNationStudent.auctionNationStudent;

        AuctionNationStudent ans = queryFactory.selectFrom(auctionNationStudent)
                .where(auctionNationStudent.auction.auctionNum.eq(auctionNum)
                        .and(auctionNationStudent.submitSeatNumber.eq(seatNum)))
                .orderBy(auctionNationStudent.submitPrice.desc(),
                        auctionNationStudent.nationStudent.creditRating.desc(),
                        auctionNationStudent.updatedDate.asc())
                .limit(1).fetchOne();
            updateWinResult(ans.getAuctionNationStudentNum(), ans.getSubmitPrice(), ans.getSubmitSeatNumber());
    }

    /**
     * 같은 자리 사람들 리스트
     * @param auctionNum
     * @param seatNum
     * @return
     */
    private List<AuctionNationStudent> getSameSeatList(Long auctionNum, int seatNum) {
        List<AuctionNationStudent> list = auctionNationStudentRepository
                .findAllBySubmitSeatNumberAndAuction_AuctionNum(seatNum, auctionNum);

        return list;
    }

    /**
     * 경매 실패하거나 신청을 안한 사람들 리스트
     * @param auctionNum
     * @return
     */
    private List<Long> getNoSeatList(Long auctionNum) {
        QAuctionNationStudent auctionNationStudent = QAuctionNationStudent.auctionNationStudent;

        List<Long> list = queryFactory.select(auctionNationStudent.auctionNationStudentNum)
                .from(auctionNationStudent)
                .where(auctionNationStudent.auction.auctionNum.eq(auctionNum)
                        .and(auctionNationStudent.resultSeatNumber.eq(0)))
                .fetch();
        return list;
    }

    void deleteBid(Long studentNum) {
        QAuctionNationStudent auctionNationStudent = QAuctionNationStudent.auctionNationStudent;

        queryFactory.delete(auctionNationStudent)
                .where(auctionNationStudent.auctionNationStudentNum.eq(studentNum))
                .execute();
    }
}

