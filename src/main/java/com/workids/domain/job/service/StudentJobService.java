package com.workids.domain.job.service;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.workids.domain.job.dto.request.RequestJobDto;
import com.workids.domain.job.dto.request.RequestStudentJobDto;
import com.workids.domain.job.dto.response.ResponseJobDto;
import com.workids.domain.job.dto.response.ResponseMyJobDto;
import com.workids.domain.job.dto.response.ResponseStudentJobDto;
import com.workids.domain.job.entity.QJob;
import com.workids.domain.job.entity.QJobNationStudent;
import com.workids.domain.job.entity.QJobToDo;
import com.workids.domain.nation.entity.QNationStudent;
import com.workids.global.config.stateType.JobStateType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentJobService {


    private final JPAQueryFactory queryFactory;

    /**
     *나라 직업 전체 조회
     */
    @Transactional
    public List<ResponseJobDto> selectByNation(RequestJobDto jobDto) {
        QJob job = QJob.job;
        QJobToDo jobToDo = QJobToDo.jobToDo;

        List<ResponseJobDto> jobList = queryFactory.select(
                        Projections.constructor(
                                ResponseJobDto.class,
                                job.name,
                                jobToDo.jobToDoContent,
                                job.salary
                        )
                )
                .from(job)
                .join(jobToDo).on(job.jobNum.eq(jobToDo.job.jobNum))
                .where(job.nation.nationNum.eq(jobDto.getNationNum()).and(job.state.eq(JobStateType.IN_USE)))
                .fetch();
        return jobList;
    }

    /**
     * 내직업조회
     */
    public List<ResponseMyJobDto> selectMyJob(RequestStudentJobDto studentJobDto) {
        QNationStudent nationStudent = QNationStudent.nationStudent;
        QJobNationStudent jobNationStudent = QJobNationStudent.jobNationStudent;
        QJob job = QJob.job;

        List<ResponseMyJobDto> myJobList = queryFactory.select(
                        Projections.constructor(
                                ResponseMyJobDto.class,
                                job.name,
                                job.salary,
                                jobNationStudent.createdDate,
                                jobNationStudent.updatedDate
                        )
                )

                .from(nationStudent)
                .join(jobNationStudent).on(nationStudent.nationStudentNum.eq(jobNationStudent.nationStudent.nationStudentNum))
                .join(job).on(jobNationStudent.job.jobNum.eq(job.jobNum))
                .where(nationStudent.nation.nationNum.eq(studentJobDto.getNationNum()).and(nationStudent.nationStudentNum.eq(studentJobDto.getNationStudentNum())))
                .orderBy(jobNationStudent.createdDate.desc())
                .fetch();
        return myJobList;

    }
}
