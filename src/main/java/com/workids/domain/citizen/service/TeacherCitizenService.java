package com.workids.domain.citizen.service;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.workids.domain.bank.entity.QBankNationStudent;
import com.workids.domain.citizen.dto.request.RequestCitizenDto;
import com.workids.domain.citizen.dto.response.ResponseCitizenCreditDto;
import com.workids.domain.citizen.dto.response.ResponseCitizenDto;
import com.workids.domain.citizen.dto.response.ResponseCitizenInfoDto;
import com.workids.domain.citizen.dto.response.ResponseImmigrantDto;
import com.workids.domain.job.entity.*;
import com.workids.domain.job.repository.JobNationStudentRepository;
import com.workids.domain.job.repository.JobRepository;
import com.workids.domain.nation.entity.NationStudent;
import com.workids.domain.nation.entity.QNationStudent;
import com.workids.domain.nation.repository.CitizenRepository;
import com.workids.domain.nation.repository.NationStudentRepository;
import com.workids.global.config.stateType.BankStateType;
import com.workids.global.config.stateType.JobStateType;
import com.workids.global.config.stateType.NationStateType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TeacherCitizenService {

    private final CitizenRepository citizenRepository;
    private final NationStudentRepository nationStudentRepository;
    private final JobNationStudentRepository jobNationStudentRepository;
    private final JobRepository jobRepository;
    private final JPAQueryFactory queryFactory;


    /**
     * 국민전체조회
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
                .leftJoin(jobNationStudent).on(nationStudent.nationStudentNum.eq(jobNationStudent.nationStudent.nationStudentNum))
                .leftJoin(job).on(jobNationStudent.job.jobNum.eq(job.jobNum))
                .join(bankNationStudent).on(nationStudent.nationStudentNum.eq(bankNationStudent.nationStudent.nationStudentNum))
                .where(nationStudent.nation.nationNum.eq(citizenDto.getNationNum()).and(bankNationStudent.state.eq(BankStateType.IN_USE)).and(nationStudent.state.eq(NationStateType.IN_NATION)).and(jobNationStudent.state.eq(JobStateType.EMPLOY)))
                .groupBy(
                        nationStudent.citizenNumber,
                        nationStudent.studentName,
                        job.name,
                        nationStudent.creditRating
                )
                .orderBy(nationStudent.citizenNumber.asc())
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
                .where(nationStudent.nation.nationNum.eq(citizenDto.getNationNum()).and(nationStudent.state.eq(NationStateType.IN_NATION)))
                .orderBy(nationStudent.citizenNumber.asc())
                .fetch();
        return creditList;
    }

    /**
     * 신용도 수정
     */
    public void updateCredit(RequestCitizenDto citizenDto) {
        QNationStudent nationStudent = QNationStudent.nationStudent;
        queryFactory.update(nationStudent)
                .set(nationStudent.creditRating, citizenDto.getCreditRating())
                .where(nationStudent.nation.nationNum.eq(citizenDto.getNationNum()).and(nationStudent.citizenNumber.eq(citizenDto.getCitizenNumber()).and(nationStudent.state.eq(NationStateType.IN_NATION))))
                .execute();
    }

    /**
     * 각각의 개인정보
     */
        @Transactional
        public List<ResponseCitizenInfoDto> citizenInfoList(RequestCitizenDto citizenDto) {
            QNationStudent nationStudent = QNationStudent.nationStudent;
            QJobNationStudent jobNationStudent = QJobNationStudent.jobNationStudent;
            QJob job = QJob.job;
            QBankNationStudent bankNationStudent = QBankNationStudent.bankNationStudent;

            List<ResponseCitizenInfoDto> citizenInfoList = queryFactory.select(
                            Projections.constructor(
                                    ResponseCitizenInfoDto.class,
                                    nationStudent.citizenNumber,
                                    nationStudent.studentName,
                                    job.name,

                                    nationStudent.creditRating
                            )
                    )
                    .from(nationStudent)
                    .leftJoin(jobNationStudent).on(nationStudent.nationStudentNum.eq(jobNationStudent.nationStudent.nationStudentNum))
                    .leftJoin(job).on(jobNationStudent.job.jobNum.eq(job.jobNum))
                    .where(nationStudent.nation.nationNum.eq(citizenDto.getNationNum()).and(nationStudent.citizenNumber.eq(citizenDto.getCitizenNumber())).and(nationStudent.state.eq(NationStateType.IN_NATION)).and(jobNationStudent.state.eq(JobStateType.EMPLOY)))
                    .fetch();
            return citizenInfoList;
        }

    /**
     * 학급번호로 이민자 조회
     */
    @Transactional
    public List<ResponseCitizenDto> selectImmigrant(RequestCitizenDto citizenDto) {
        QNationStudent nationStudent = QNationStudent.nationStudent;
        QJobNationStudent jobNationStudent = QJobNationStudent.jobNationStudent;
        QJob job = QJob.job;
        QBankNationStudent bankNationStudent = QBankNationStudent.bankNationStudent;

        List<ResponseCitizenDto> selectImmigrant = queryFactory.select(
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
                .leftJoin(jobNationStudent).on(nationStudent.nationStudentNum.eq(jobNationStudent.nationStudent.nationStudentNum))
                .leftJoin(job).on(jobNationStudent.job.jobNum.eq(job.jobNum))
                .leftJoin(bankNationStudent).on(nationStudent.nationStudentNum.eq(bankNationStudent.nationStudent.nationStudentNum))
                .where(job.nation.nationNum.eq(citizenDto.getNationNum()).and(nationStudent.citizenNumber.eq(citizenDto.getCitizenNumber())).and(job.state.eq(JobStateType.IN_USE)).and(jobNationStudent.state.eq(JobStateType.EMPLOY)))
                .groupBy(
                        nationStudent.citizenNumber,
                        nationStudent.studentName,
                        job.name,
                        nationStudent.creditRating
                )
                .fetch();
        return selectImmigrant;
    }

    /**
     * 학급번호로 이민자 조회(국적 이탈)
     */
    @Transactional
    public List<ResponseCitizenDto> selectImmigrantLeave(RequestCitizenDto citizenDto) {
        QNationStudent nationStudent = QNationStudent.nationStudent;
        QJobNationStudent jobNationStudent = QJobNationStudent.jobNationStudent;
        QJob job = QJob.job;
        QBankNationStudent bankNationStudent = QBankNationStudent.bankNationStudent;

        List<ResponseCitizenDto> selectImmigrantLeave = queryFactory.select(
                        Projections.constructor(
                                ResponseCitizenDto.class,
                                nationStudent.citizenNumber,
                                nationStudent.studentName,
                                job.name,
                                nationStudent.creditRating
                        )
                )
                .from(nationStudent)
                .leftJoin(jobNationStudent).on(nationStudent.nationStudentNum.eq(jobNationStudent.nationStudent.nationStudentNum))
                .leftJoin(job).on(jobNationStudent.job.jobNum.eq(job.jobNum))
                .leftJoin(bankNationStudent).on(nationStudent.nationStudentNum.eq(bankNationStudent.nationStudent.nationStudentNum))
                .where(job.nation.nationNum.eq(citizenDto.getNationNum()).and(nationStudent.citizenNumber.eq(citizenDto.getCitizenNumber())).and(job.state.eq(JobStateType.IN_USE)).and(jobNationStudent.state.eq(JobStateType.EMPLOY)))
                .groupBy(
                        nationStudent.citizenNumber,
                        nationStudent.studentName,
                        job.name,
                        nationStudent.creditRating
                )
                .fetch();
        return selectImmigrantLeave;
    }


    /**
     * 국적이탈
     */
    public void immigrantLeave(RequestCitizenDto citizenDto) {
        QNationStudent nationStudent = QNationStudent.nationStudent;

                queryFactory.update(nationStudent)
                        .set(nationStudent.state, 1)
                .where(nationStudent.citizenNumber.eq(citizenDto.getCitizenNumber()).and(nationStudent.nation.nationNum.eq(citizenDto.getNationNum())))
                .execute();
    }

    /**
     * 취득신고
     */
    public void immigrantJoin(RequestCitizenDto citizenDto) {
        QNationStudent nationStudent = QNationStudent.nationStudent;
        QBankNationStudent bankNationStudent = QBankNationStudent.bankNationStudent;
        QJobNationStudent jobNationStudent = QJobNationStudent.jobNationStudent;
        NationStudent nationStudent1 = nationStudentRepository.findByCitizenNumber(citizenDto.getCitizenNumber());
        Job job = jobRepository.findByNation_NationNumAndName(citizenDto.getNationNum(), citizenDto.getName());

        queryFactory.update(nationStudent)
                .set(nationStudent.creditRating, citizenDto.getCreditRating())
                .set(nationStudent.state,0)
                .where(nationStudent.nation.nationNum.eq(citizenDto.getNationNum()).and(nationStudent.citizenNumber.eq(citizenDto.getCitizenNumber())))
                .execute();

        queryFactory.update(bankNationStudent)
                .set(bankNationStudent.balance, citizenDto.getAsset())
                .where(bankNationStudent.nationStudent.nationStudentNum.eq(nationStudent1.getNationStudentNum()))
                .execute();

        queryFactory.update(jobNationStudent)
                .set(jobNationStudent.job.jobNum, job.getJobNum())
                .where(jobNationStudent.nationStudent.nationStudentNum.eq(nationStudent1.getNationStudentNum()))
                .execute();

        //JobNationStudent jobNationStudent = JobNationStudent.toEntity(job,nationStudent1);
        //jobNationStudentRepository.save(jobNationStudent);


    }
}


