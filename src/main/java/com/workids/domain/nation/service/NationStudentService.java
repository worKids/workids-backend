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

import static com.workids.domain.nation.entity.QCitizen.citizen;

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

    private final JPAQueryFactory queryFactory;
    //private final QBankNationStudent bankNationStudent;

    private final CitizenService citizenService;



    /**
     * 나라-학생 관계 등록
     * : 가입 여부 확인 후 생성(나라코드, 학생존재, 국민목록에 존재 여부 확인)
     */
    @Transactional
    public void join(RequestNationStudentJoinDto dto) {


        Nation nation = nationRepository.findByNationNum(dto.getNationNum())
                .orElseThrow(() -> new ApiException(ExceptionEnum.NATION_NOT_EXIST_EXCEPTION));

        // 나라코드 일치여부 확인
        if (!checkNationCode(nation.getCode(), dto.getCode())) {
            throw new ApiException(ExceptionEnum.NATION_CODE_NOT_MATCH_EXCEPTION);
        }

        // 가입여부 확인
        Student student = studentRepository.findByStudentNum(dto.getStudentNum());
        if(student == null){
            throw new ApiException(ExceptionEnum.NATION_STUDENT_NOT_EXIST_EXCEPTION);
        }

        Citizen citizen = citizenRepository.findByCitizenNumber(dto.getCitizenNumber());
        if(citizen == null){
            throw new ApiException(ExceptionEnum.NATION_NOT_JOIN_EXCEPTION);
        }

        String[] birth = student.getRegistNumber().split("-"); // 생년월일

        // 생년월일(고유키)로 확인
        if(!citizen.getBirthDate().equals(birth[0])){
           throw new ApiException(ExceptionEnum.NATION_NOT_JOIN_EXCEPTION);
        }

        // 국민 수
        int totalCitizen = citizenService.citizenCount(dto.getNationNum());

        NationStudent nationStudent = NationStudent.of(dto, student, nation, totalCitizen);
        nationStudentRepository.save(nationStudent);
        System.out.println("nationStudent 등록 완료");

        // 계좌번호 생성
        String accountNumber = AccountNumberGenerator.createRandomNumber(nation.getNationNum().intValue(), 1, nationStudent.getNationStudentNum().intValue());

        System.out.println("계좌번호: " + accountNumber);
        // 계좌번호 중복 체크
        while(checkAccountNumber(accountNumber)){
            // 계좌번호 중복(난수 발생 실패) -> 계좌번호 생성 재시도
            accountNumber = AccountNumberGenerator.createRandomNumber(nation.getNationNum().intValue(), 1, nationStudent.getNationStudentNum().intValue());
        }

        // 은행-나라-학생 생성(주거래통장 생성)
        createMainAcount(nationStudent, accountNumber, nationStudent.getCreatedDate(), nation.getEndDate());

        System.out.println("은행-나라-학생 생성 완료");


    }

    /**
     * 가입 여부 확인
     * : citizen 테이블에 학급번호 존재하면 가입 가능
     */
    /*
    public boolean isJoin(Citizen citizens, int citizenNumber){
        for(Citizen citizen : citizens){
            if(citizen.getCitizenNum() == citizenNumber){
                return true;
            }
        }
        return false;
    }

     */

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
