package com.workids.domain.bank.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.workids.domain.bank.dto.request.RequestBankStudentCreateDto;
import com.workids.domain.bank.dto.response.ResponseStudentBankDto;
import com.workids.domain.bank.entity.Bank;
import com.workids.domain.bank.entity.BankNationStudent;
import com.workids.domain.bank.entity.TransactionHistory;
import com.workids.domain.bank.module.AccountNumberGenerator;
import com.workids.domain.bank.repository.BankNationStudentRepository;
import com.workids.domain.bank.repository.BankRepository;
import com.workids.domain.bank.repository.TransactionHistoryRepository;
import com.workids.domain.nation.entity.Nation;
import com.workids.domain.nation.entity.NationStudent;
import com.workids.domain.nation.repository.NationRepository;
import com.workids.domain.nation.repository.NationStudentRepository;
import com.workids.global.config.stateType.BankStateType;
import com.workids.global.exception.ApiException;
import com.workids.global.exception.ExceptionEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.workids.domain.bank.entity.QBank.bank;
import static com.workids.domain.bank.entity.QBankNationStudent.bankNationStudent;

/**
 * Student 은행 Service
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StudentBankService {
    private final BankRepository bankRepository;
    private final NationStudentRepository nationStudentRepository;
    private final NationRepository nationRepository;
    private final BankNationStudentRepository bankNationStudentRepository;
    private final TransactionHistoryRepository transactionHistoryRepository;

    private final JPAQueryFactory queryFactory;

    /**
     * 전체 은행 상품 조회(현재 사용중 모두 조회, 주거래 통장 상품 제외)
     */
    @Transactional
    public List<ResponseStudentBankDto> getBankList(Long nationNum){
        // 전체 항목 조회(사용중, 주거래 통장 상품 제외)-상품 유형으로 정렬
        // Entity 리스트로 결과
        List<Bank> bankProductList = queryFactory.selectFrom(bank)
                .where(bank.nation.nationNum.eq(nationNum),
                        bank.productType.ne(BankStateType.MAIN_ACCOUNT),
                        bank.productState.eq(BankStateType.IN_USE))
                .orderBy(bank.productType.asc(), bank.productNum.asc())
                .fetch();

        // Dto 리스트로 변환
        List<ResponseStudentBankDto> resultList = new ArrayList<>();
        bankProductList.forEach(b-> {
            System.out.println(b); // 결과 확인
            // 만기일이 나라 종료일을 지나면 개설 불가하도록

            resultList.add(ResponseStudentBankDto.toDto(b));
        });

        return resultList;
    }

    /**
     * 은행 상품 가입(예금)
     */
    @Transactional
    public void createBankDeposit(RequestBankStudentCreateDto dto){
        // 은행 상품 정보 조회
        Long productNum = dto.getProductNum();
        Bank joinBank = bankRepository.findById(productNum).orElseThrow(() -> new ApiException(ExceptionEnum.BANK_NOT_EXIST_EXCEPTION));
        System.out.println("입력한 상품번호: "+productNum);
        System.out.println(joinBank);

        // 나라-학생 정보 조회
        Long nationStudentNum = dto.getNationStudentNum();
        NationStudent nationStudent = nationStudentRepository.findById(nationStudentNum).orElseThrow(() -> new ApiException(ExceptionEnum.NATION_STUDENT_NOT_EXIST_EXCEPTION));
        System.out.println("나라-학생 고유 번호: "+nationStudentNum);
        System.out.println(nationStudent);

        // 나라 정보 조회-나라 종료일
        Long nationNum = nationStudent.getNation().getNationNum(); // 나라 고유 번호
        Nation nation = nationRepository.findById(nationNum).orElseThrow(() -> new ApiException(ExceptionEnum.NATION_NOT_EXIST_EXCEPTION));
        LocalDateTime nationEndDateTime = nation.getEndDate(); // 나라 종료일
        // =========================================================================================================
        // 개설일, 만기일 확인
        // ========== LocalDateTime ==========
        // 개설일
        LocalDateTime createdDateTime = LocalDateTime.now();
        System.out.println("개설일 출력: "+ createdDateTime);
        
        // 만기일 = 오전 1시
        LocalDateTime endDateTime = createdDateTime.plusWeeks(joinBank.getProductPeriod()); // 해당 은행 상품 가입 기간(주 단위) 계산
        endDateTime = endDateTime.withHour(1);
        endDateTime = endDateTime.withMinute(0);
        endDateTime = endDateTime.withSecond(0);
        endDateTime = endDateTime.withNano(0);
        System.out.println("만기일 출력: "+ endDateTime);

        // ========== LocalDate ==========
        // 만기일
        LocalDate endDate = endDateTime.toLocalDate();
        System.out.println("만기일 날짜만 출력 "+endDate);

        // 나라 종료일
        LocalDate nationEndDate = nationEndDateTime.toLocalDate();
        System.out.println("나라 종료일 날짜만 출력 "+nationEndDate);

        // 나라 종료일보다 만기일이 클 경우 은행 상품 가입 불가
        if (nationEndDate.compareTo(endDate) < 0){
            throw new ApiException(ExceptionEnum.BANK_NOT_VALID_PRODUCT_CREATE_EXCEPTION);
        }
        // =========================================================================================================
        Long depositAmount = dto.getDepositAmount(); // 예금 금액
        // 유효하지 않은 예금 금액인 경우 은행 상품 가입 불가
        if (depositAmount<=0){
            throw new ApiException(ExceptionEnum.BANK_NOT_VALID_AMOUNT_CREATE_EXCEPTION);
        }

        // 주거래 통장 금액이 예금 금액보다 적은 경우 은행 상품 가입 불가
        List<BankNationStudent> mainAccountList = queryFactory.selectFrom(bankNationStudent)
                .join(bankNationStudent.bank, bank)
                .where(bankNationStudent.nationStudent.nationStudentNum.eq(nationStudentNum),
                        bank.productType.eq(BankStateType.MAIN_ACCOUNT))
                .fetch();
        BankNationStudent mainAccountBankNationStudent = mainAccountList.get(0); // 주거래 통장
        System.out.println("주거래 통장 정보");
        System.out.println(mainAccountBankNationStudent);
        Long mainAccountBalance = mainAccountBankNationStudent.getBalance(); // 주거래 통장 잔액

        if (mainAccountBalance < depositAmount){
            throw new ApiException(ExceptionEnum.BANK_NOT_ENOUGH_AMOUNT_CREATE_EXCEPTION);
        }
        // =========================================================================================================
        // 고유한 계좌번호 생성
        String accountNumber;
        while(true){
            accountNumber = AccountNumberGenerator.createRandomNumber(nationNum.intValue(), productNum.intValue(), nationStudentNum.intValue());
            System.out.println("생성한 난수 출력: " + accountNumber);

            // 계좌번호 이미 존재하는지 확인
            List<BankNationStudent> bankNationStudentList = queryFactory.selectFrom(bankNationStudent)
                    .where(bankNationStudent.accountNumber.eq(accountNumber))
                    .fetch();

            if (bankNationStudentList.isEmpty()) // 아직 존재하지 않으면
                break;
        }
        // =========================================================================================================
        // 은행 상품 가입
        BankNationStudent newBankNationStudent = BankNationStudent.of(joinBank, nationStudent, accountNumber, depositAmount, BankStateType.MAINTAIN, createdDateTime, endDateTime);
        bankNationStudentRepository.save(newBankNationStudent);

        // 주거래 통장에서 예금 금액만큼 이체
        mainAccountBankNationStudent.updateBalance(mainAccountBalance-depositAmount);

        // 예금 통장 transaction 생성
        TransactionHistory newTransactionHistory = TransactionHistory.of(newBankNationStudent, "예금 신규 가입", BankStateType.CATEGORY_JOIN, BankStateType.DEPOSIT, depositAmount);
        transactionHistoryRepository.save(newTransactionHistory);
    }
}
