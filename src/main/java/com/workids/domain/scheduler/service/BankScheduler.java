package com.workids.domain.scheduler.service;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.workids.domain.bank.entity.BankNationStudent;
import com.workids.domain.bank.entity.TransactionHistory;
import com.workids.domain.bank.repository.TransactionHistoryRepository;
import com.workids.domain.bank.service.StudentBankService;
import com.workids.domain.scheduler.vo.BankExpiredVo;
import com.workids.global.config.stateType.BankStateType;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.workids.domain.bank.entity.QBank.bank;
import static com.workids.domain.bank.entity.QBankNationStudent.bankNationStudent;

/**
 * 은행 Scheduler
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BankScheduler {
    private final StudentBankService studentBankService;

    private final TransactionHistoryRepository transactionHistoryRepository;

    private final JPAQueryFactory queryFactory;

    /**
     * 주거래 계좌 만기 처리 Scheduler
     * 매일 오전 12시 10분에 수행
     */
    @Transactional
    @Scheduled(cron = "0 10 0 * * *", zone = "Asia/Seoul")
    public void updateExpiredMainBank() {
        System.out.println("주거래 계좌 만기 처리 스케줄러 실행");

        // 시간 범위
        LocalDate yesterday = LocalDate.now().minusDays(1); // 하루 전
        LocalDateTime yesterdayStart = yesterday.atStartOfDay();
        LocalDateTime yesterdayEnd = LocalDateTime.of(yesterday, LocalTime.MAX).withNano(0);

        // 만기 처리해야 하는 주거래 계좌 정보
        List<BankNationStudent> bankExpiredList;
        bankExpiredList = queryFactory.selectFrom(bankNationStudent)
                .join(bank).on(bankNationStudent.bank.productNum.eq(bank.productNum))
                .where(bank.productType.eq(BankStateType.MAIN_ACCOUNT),
                        bankNationStudent.state.eq(BankStateType.IN_USE),
                        bankNationStudent.endDate.between(yesterdayStart, yesterdayEnd))
                .fetch();

        // 주거래 계좌 상태 업데이트
        bankExpiredList.forEach((b)->{
            System.out.println(b);
            b.updateExpiredState(BankStateType.EXPIRE);
        });
    }

    /**
     * 예금 계좌 만기 처리 Scheduler
     * 매일 오전 1시 0분에 수행
     */
    @Transactional
    @Scheduled(cron = "0 0 1 * * *", zone = "Asia/Seoul")
    public void updateExpiredDepositBank(){
        System.out.println("예금 계좌 만기 처리 스케줄러 실행");
        
        // 시간 범위
        LocalDate now = LocalDate.now();
        LocalDateTime nowStart = now.atStartOfDay();
        LocalDateTime nowEnd = LocalDateTime.of(now, LocalTime.MAX).withNano(0);

        // 만기 처리해야 하는 예금 계좌 정보
        List<BankExpiredVo> bankExpiredVoList;
        bankExpiredVoList = queryFactory.select(
                                Projections.constructor(
                                        BankExpiredVo.class,
                                        bankNationStudent.bankNationStudentNum,
                                        bankNationStudent.nationStudent.nationStudentNum,
                                        bankNationStudent.balance,
                                        bank.interestRate
                                )
                ).from(bankNationStudent)
                .join(bank).on(bankNationStudent.bank.productNum.eq(bank.productNum))
                .where(bank.productType.eq(BankStateType.DEPOSIT_ACCOUNT),
                        bankNationStudent.state.eq(BankStateType.IN_USE),
                        bankNationStudent.endDate.between(nowStart, nowEnd))
                .fetch();

        // 각 예금 계좌 만기 처리
        bankExpiredVoList.forEach(b->{
            System.out.println(b);
            Long bankNationStudentNum = b.getBankNationStudentNum(); // 은행-나라-학생 고유 번호
            Long nationStudentNum = b.getNationStudentNum(); // 나라-학생 고유 번호
            Long depositAmount = b.getBalance(); // 예금 금액
            double interestRate = b.getInterestRate(); // 만기 이자율

            // 예금 계좌
            BankNationStudent depositAccountBankNationStudent = queryFactory.selectFrom(bankNationStudent)
                    .where(bankNationStudent.bankNationStudentNum.eq(bankNationStudentNum))
                    .fetchOne();

            // 예금 계좌 상태 업데이트
            depositAccountBankNationStudent.updateExpiredState(BankStateType.EXPIRE);
            
            // 주거래 계좌
            BankNationStudent mainAccountBankNationStudent = studentBankService.findMainAccountByNationStudentNum(nationStudentNum);

            // 주거래 계좌 입금 transaction 생성-예금 금액
            TransactionHistory mainTransactionHistory = TransactionHistory.of(mainAccountBankNationStudent, "예금 만기 입금", BankStateType.CATEGORY_TRANSFER, BankStateType.DEPOSIT, depositAmount);
            transactionHistoryRepository.save(mainTransactionHistory);

            // 이자 금액 계산
            long interestAmount = (long)(depositAmount * (interestRate / 100));

            // 주거래 계좌 입금 transaction 생성-이자 금액
            TransactionHistory interestTransactionHistory = TransactionHistory.of(mainAccountBankNationStudent, "예금 만기 이자", BankStateType.CATEGORY_INTEREST, BankStateType.DEPOSIT, interestAmount);
            transactionHistoryRepository.save(interestTransactionHistory);

            // 주거래 계좌로 예금 금액+이자 금액만큼 이체
            Long mainAccountBalance = mainAccountBankNationStudent.getBalance(); // 주거래 계좌 잔액
            mainAccountBankNationStudent.updateBalance(mainAccountBalance+depositAmount+interestAmount);
        });
    }
}
