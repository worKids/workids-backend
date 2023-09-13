package com.workids.domain.nation.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.workids.domain.nation.dto.request.RequestNationStudentJoinDto;
import com.workids.domain.nation.entity.*;
import com.workids.domain.nation.repository.CitizenRepository;
import com.workids.domain.nation.repository.NationRepository;
import com.workids.domain.nation.repository.NationStudentRepository;
import com.workids.domain.user.entity.Student;
import com.workids.domain.user.repository.StudentRepository;
import com.workids.global.exception.ApiException;
import com.workids.global.exception.ExceptionEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NationStudentService {

    private final NationStudentRepository nationStudentRepository;
    private final CitizenRepository citizenRepository;
    private final StudentRepository studentRepository;
    private final NationRepository nationRepository;

    private final JPAQueryFactory queryFactory;

    /**
     * 나라-학생 관계 등록
     * : 가입 여부 확인 후 생성
     */
    @Transactional
    public void join(RequestNationStudentJoinDto dto) {

        List<Citizen> citizens = citizenRepository.findByNation_NationNum(dto.getNationNum());

        System.out.println(citizens.toString());
        for (Citizen citizen : citizens) {
            System.out.println("citizen: " + citizen);
        }
        // 가입여부 확인
        if (!isJoin(citizens, dto.getCitizenNumber())) {
            // 가입 불가능
            throw new ApiException(ExceptionEnum.NATIONSTUDENT_JOIN_EXCEPTION);
        }

        Student student = studentRepository.findByStudentNum(dto.getStudentNum());
        Nation nation = nationRepository.findByNationNum(dto.getNationNum());

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
}
