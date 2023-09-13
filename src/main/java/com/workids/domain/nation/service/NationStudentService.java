package com.workids.domain.nation.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.workids.domain.bank.entity.Bank;
import com.workids.domain.bank.entity.BankNationStudent;
import com.workids.domain.bank.entity.QBankNationStudent;
import com.workids.domain.bank.module.AccountNumberGenerator;
import com.workids.domain.bank.repository.BankNationStudentRepository;
import com.workids.domain.bank.repository.BankRepository;
import com.workids.domain.nation.dto.request.RequestNationStudentJoinDto;
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
import java.util.List;
//import static com.workids.domain.bank.entity.QBankNationStudent.bankNationStudent;

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

    //private final JPAQueryFactory queryFactory;
    //private final QBankNationStudent bankNationStudent;

    /**
     * 나라-학생 관계 등록
     * : 가입 여부 확인 후 생성
     */
    @Transactional
    public void join(RequestNationStudentJoinDto dto) {

        List<Citizen> citizens = citizenRepository.findByNation_NationNum(dto.getNationNum());

        System.out.println(citizens.toString());
        for (Citizen citizen : citizens) {
            System.out.println("citizen: " + citizen.getCitizenNum());
        }
        // 가입여부 확인
        if (!isJoin(citizens, dto.getCitizenNumber())) {
            // 가입 불가능
            throw new ApiException(ExceptionEnum.NATIONSTUDENT_JOIN_EXCEPTION);
        }

        Student student = studentRepository.findByStudentNum(dto.getStudentNum());
        if(student == null){
            // 임시 예외
            throw new IllegalAccessError("stdent를 찾지 못함");
        }
        Nation nation = nationRepository.findByNationNum(dto.getNationNum())
                .orElseThrow(() -> new ApiException(ExceptionEnum.NATION_NOT_EXIST_EXCEPTION));

        if(citizens.isEmpty()){
            throw new IllegalAccessError("citizens이 없음");
        }
        int citizenNumber = 0;
        for (Citizen citizen : citizens) {
            if (citizen.getName().equals(student.getName())) {
                citizenNumber = citizen.getCitizenNumber();
                break;
            }
        }


        NationStudent nationStudent = NationStudent.of(dto, student, nation, citizenNumber);
        nationStudentRepository.save(nationStudent);
        System.out.println("nationStudent 등록 완료");

        // 계좌번호 생성
        String accountNumber = AccountNumberGenerator.createRandomNumber(nation.getNationNum().intValue(), 1, nationStudent.getNationStudentNum().intValue());

        System.out.println("계좌번호: " + accountNumber);
        // 계좌번호 중복 체크
        while(checkAccountNumber(accountNumber)){
            // 계좌번호 중복(난수 발생 실패) -> 계좌번호 생성 재시도
            //new ApiException(ExceptionEnum.BANKNATIONSTUDENT_NOT_CREATE_EXCEPTION);
            accountNumber = AccountNumberGenerator.createRandomNumber(nation.getNationNum().intValue(), 1, nationStudent.getNationStudentNum().intValue());
        }

/*
        BankNationStudent bns = queryFactory.selectFrom(bankNationStudent)
                .where(bankNationStudent.accountNumber.eq(accountNumber))
                .fetchOne();

        while(bns == null){
            accountNumber = AccountNumberGenerator.createRandomNumber(nation.getNationNum().intValue(), 1, nationStudent.getNationStudentNum().intValue());
        }

 */

        // 은행-나라-학생 생성(주거래통장 생성)
        createMainAcount(nationStudent, accountNumber, nationStudent.getCreatedDate(), nation.getEndDate());

        System.out.println("은행-나라-학생 생성 완료");


    }

    /**
     * 가입 여부 확인
     * : citizen 테이블에 학급번호 존재하면 가입 가능
     */
    public boolean isJoin(List<Citizen> citizens, int citizenNumber){
        for(Citizen citizen : citizens){
            if(citizen.getCitizenNum() == citizenNumber){
                return true;
            }
        }
        return false;
    }

    /**
     * 은행-나라-학생 생성(주거래통장 생성)
     */
    public void createMainAcount(NationStudent nationStudent, String accountNumber, LocalDateTime createDate, LocalDateTime endDate){

        Bank bank = bankRepository.findById(1L).orElse(null); // default 은행상품 PK = 1

        bankNationStudentRepository.save(BankNationStudent.of(bank, nationStudent, accountNumber, 0, 0, createDate, endDate));

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

}
