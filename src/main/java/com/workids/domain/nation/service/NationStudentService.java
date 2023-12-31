package com.workids.domain.nation.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.workids.domain.bank.entity.Bank;
import com.workids.domain.bank.entity.BankNationStudent;
import com.workids.domain.bank.entity.TransactionHistory;
import com.workids.domain.bank.module.AccountNumberGenerator;
import com.workids.domain.bank.repository.BankNationStudentRepository;
import com.workids.domain.bank.repository.BankRepository;
import com.workids.domain.bank.repository.TransactionHistoryRepository;
import com.workids.domain.nation.dto.request.RequestNationStudentDto;
import com.workids.domain.nation.dto.request.RequestNationStudentJoinDto;
import com.workids.domain.nation.dto.response.ResponseNationStudentDto;
import com.workids.domain.nation.entity.*;
import com.workids.domain.nation.repository.CitizenRepository;
import com.workids.domain.nation.repository.NationRepository;
import com.workids.domain.nation.repository.NationStudentRepository;
import com.workids.domain.user.entity.Student;
import com.workids.domain.user.repository.StudentRepository;
import com.workids.global.config.stateType.BankStateType;
import com.workids.global.exception.ApiException;
import com.workids.global.exception.ExceptionEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NationStudentService {

    private final NationStudentRepository nationStudentRepository;
    private final CitizenRepository citizenRepository;
    private final StudentRepository studentRepository;
    private final NationRepository nationRepository;
    private final BankRepository bankRepository;

    private final BankNationStudentRepository bankNationStudentRepository;
    private final TransactionHistoryRepository transactionHistoryRepository;



    /**
     * 나라-학생 관계 등록
     * : 가입 여부 확인 후 생성(나라코드, 학생존재, 국민목록에 존재 여부 확인)
     */
    @Transactional
    public void join(RequestNationStudentJoinDto dto) {

        // 나라코드로 nationNum 찾기
        Nation nation = nationRepository.findByCode(dto.getCode());

        if(nation == null){
            throw new ApiException(ExceptionEnum.NATION_NOT_EXIST_EXCEPTION);
        }

        // 학생 가입여부 확인
        Student student = studentRepository.findByStudentNum(dto.getStudentNum());
        if(student == null){
            throw new ApiException(ExceptionEnum.NATION_STUDENT_NOT_EXIST_EXCEPTION);
        }

        // nationStudent에 가입된 회원인지 확인
        NationStudent ns = nationStudentRepository.findByStudent_StudentNumAndNation_NationNum(nation.getNationNum(), dto.getStudentNum());
        if(ns != null){
            throw new ApiException(ExceptionEnum.NATION_STUDENT_EXIST_EXCEPTION);
        }

        // 나라, 학급번호로 가입 가능 여부 확인
        Citizen citizen = citizenRepository.findByCitizenNumberAndNation_NationNum(dto.getCitizenNumber(), nation.getNationNum());
        if(citizen == null){
            throw new ApiException(ExceptionEnum.NATION_NOT_JOIN_EXCEPTION);
        }

        String[] birth = student.getRegistNumber().split("-"); // 생년월일

        // 생년월일(고유키)로 확인
        if(!citizen.getBirthDate().equals(birth[0])){
           throw new ApiException(ExceptionEnum.NATION_NOT_JOIN_EXCEPTION);
        }

        NationStudent nationStudent = NationStudent.of(dto, student, nation);
        nationStudentRepository.save(nationStudent);

        // 계좌번호 생성
        String accountNumber = AccountNumberGenerator.createRandomNumber(nation.getNationNum().intValue(), 1, nationStudent.getNationStudentNum().intValue());

        System.out.println("계좌번호: " + accountNumber);
        // 계좌번호 중복 체크
        while(checkAccountNumber(accountNumber)){
            // 계좌번호 중복(난수 발생 실패) -> 계좌번호 생성 재시도
            accountNumber = AccountNumberGenerator.createRandomNumber(nation.getNationNum().intValue(), 1, nationStudent.getNationStudentNum().intValue());
        }

        // 은행-나라-학생 생성(주거래통장 생성)
        BankNationStudent mainBankNationStudent = createMainAcount(nationStudent, accountNumber, nation.getBalance(), nationStudent.getCreatedDate(), nation.getEndDate());

        // 주거래통장 계좌 입금(default 자산 transaction 생성)
        TransactionHistory newTransactionHistory = TransactionHistory.of(mainBankNationStudent, "나라 가입 축하 지원금", BankStateType.CATEGORY_SUPPORT, BankStateType.DEPOSIT, nation.getBalance());
        transactionHistoryRepository.save(newTransactionHistory);

        System.out.println("은행-나라-학생 생성 완료");


    }


    /**
     * 나라코드 일치 확인
     */
    @Transactional
    public boolean checkNationCode(String dbCode, String code){
        if(dbCode.equals(code)){
            return true;
        }
        return false;

    }


    /**
     * 은행-나라-학생 생성(주거래통장 생성)
     */
    @Transactional
    public BankNationStudent createMainAcount(NationStudent nationStudent, String accountNumber, Long balance, LocalDateTime createDate, LocalDateTime endDate){

        Bank bank = bankRepository.findById(1L).orElse(null); // default 은행상품 PK = 1

        BankNationStudent bankNationStudent = bankNationStudentRepository.save(BankNationStudent.of(bank, nationStudent, accountNumber, balance, 0, createDate, endDate));

        return bankNationStudent;
    }

    /**
     * 계좌번호 중복 체크
     * @return false : 계좌번호 존재하지 않는 경우
     * @return true : 계좌번호 존재하는 경우
     */
    @Transactional
    public boolean checkAccountNumber(String accountNumber){
        if(bankNationStudentRepository.findByAccountNumber(accountNumber) == null){
            return false; // 계좌번호 존재하지 않는 경우
        }
        return true;

    }

    /**
     * 나라학생 번호 조회
     * @param dto
     * @return
     */
    public ResponseNationStudentDto getNationStudentNum(RequestNationStudentDto dto) {
        NationStudent student = nationStudentRepository
                .findByStudent_StudentNumAndNation_NationNum( dto.getStudentNum(), dto.getNationNum());
        if (student == null) {
            throw new ApiException(ExceptionEnum.STUDENT_NOT_MATCH_EXCEPTION);
        }
        return ResponseNationStudentDto.toDto(student);
    }
}
