package com.workids.domain.citizen.service;


import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.workids.domain.bank.entity.QBankNationStudent;
import com.workids.domain.citizen.dto.request.RequestCitizenDto;
import com.workids.domain.citizen.dto.response.ResponseCitizenCreditDto;
import com.workids.domain.citizen.dto.response.ResponseCitizenDto;
import com.workids.domain.job.entity.*;
import com.workids.domain.nation.entity.NationStudent;
import com.workids.domain.nation.entity.QNationStudent;
import com.workids.domain.nation.repository.CitizenRepository;
import com.workids.global.config.stateType.JobStateType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TeacherCitizenService {

    private final CitizenRepository citizenRepository;
    private final JPAQueryFactory queryFactory;


    /**
     * 나라의 직업전체조회
     */
    @Transactional
    public List<ResponseCitizenDto> citizenList(RequestCitizenDto citizenDto) {
        QNationStudent nationStudent = QNationStudent.nationStudent;
        QJobNationStudent jobNationStudent = QJobNationStudent.jobNationStudent;
        QJob job = QJob.job;
        QBankNationStudent bankNationStudent = QBankNationStudent.bankNationStudent;

        List<ResponseCitizenDto> citizenList = queryFactory.select(
                        Projections.constructor(
                                ResponseCitizenDto.class,
                                nationStudent.citizenNumber,
                                nationStudent.studentName,
                                job.name,
                                bankNationStudent.balance.sum(),
                                nationStudent.creditRating
                        )
                )
                .from(nationStudent)
                .join(jobNationStudent).on(nationStudent.nationStudentNum.eq(jobNationStudent.nationStudent.nationStudentNum))
                .join(job).on(jobNationStudent.job.jobNum.eq(job.jobNum))
                .join(bankNationStudent).on(nationStudent.nationStudentNum.eq(bankNationStudent.nationStudent.nationStudentNum))
                .where(job.nation.nationNum.eq(citizenDto.getNationNum()).and(job.state.eq(JobStateType.IN_USE)))
                .fetch();
        return citizenList;
    }

    /**
     * 신용도 전체 조회
     */
    @Transactional
    public List<ResponseCitizenCreditDto> creditList(RequestCitizenDto citizenDto) {
        QNationStudent nationStudent = QNationStudent.nationStudent;
        List<ResponseCitizenCreditDto> creditList = queryFactory.select(
                        Projections.constructor(
                                ResponseCitizenCreditDto.class,
                                nationStudent.citizenNumber,
                                nationStudent.studentName,
                                nationStudent.creditRating
                        )
                )
                .from(nationStudent)
                .where(nationStudent.nation.nationNum.eq(citizenDto.getNationNum()))
                .fetch();
        return creditList;
    }

    /**
     * 신용도 수정
     */
    public void updateCredit(RequestCitizenDto citizenDto) {

        citizenRepository.update(citizenDto.getCreditRating(), citizenDto.getNationNum(), citizenDto.getCitizenNumber());

    }
}


