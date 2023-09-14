package com.workids.domain.job.service;


import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.workids.domain.job.dto.request.RequestJobDto;
import com.workids.domain.job.dto.request.RequestStudentJobDto;
import com.workids.domain.job.dto.response.ResponseJobDto;
import com.workids.domain.job.dto.response.ResponseStudentJobDto;
import com.workids.domain.job.entity.*;
import com.workids.domain.job.repository.JobNationStudentRepository;
import com.workids.domain.job.repository.JobRepository;
import com.workids.domain.job.repository.JobToDoRepository;
import com.workids.domain.nation.entity.Nation;
import com.workids.domain.nation.entity.NationStudent;
import com.workids.domain.nation.entity.QNationStudent;
import com.workids.domain.nation.repository.NationRepository;
import com.workids.domain.nation.repository.NationStudentRepository;
import com.workids.global.config.stateType.JobStateType;
import com.workids.global.config.stateType.NationStateType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TeacherJobService {

    private final JobRepository jobRepository;
    private final JobToDoRepository jobToDoRepository;
    private final JobNationStudentRepository jobNationStudentRepository;
    private final NationRepository nationRepository;
    private final JPAQueryFactory queryFactory;
    private final NationStudentRepository nationStudentRepository;

    /**
     * 나라의 직업전체조회
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
     * 직업 생성
     */
    public void createJob(RequestJobDto jobDto) {
        Nation nation =nationRepository.findById(jobDto.getNationNum()).orElse(null);

        Job job = Job.toEntity(nation, jobDto);
        Job jobInfo = jobRepository.save(job);
        JobToDo jobToDo =JobToDo.toEntity(jobInfo,jobDto);
        jobToDoRepository.save(jobToDo);
    }

    /**
     * 직업 삭제
     */
    public void delete(RequestJobDto jobDto) {

       jobRepository.delete(jobDto.getJobNum());
    }

    /**
     * 직업부여리스트
     */
    @Transactional
    public List<ResponseStudentJobDto> studentJobList(RequestStudentJobDto studentjobDto) {

        QNationStudent nationStudent = QNationStudent.nationStudent;
        QJobNationStudent jobNationStudent = QJobNationStudent.jobNationStudent;
        QJob job = QJob.job;

        List<ResponseStudentJobDto> studentJobList = queryFactory.select(
                        Projections.constructor(
                                ResponseStudentJobDto.class,
                                nationStudent.citizenNumber,
                                nationStudent.studentName,
                                job.name
                        )
                )
                .from(nationStudent)
                .join(jobNationStudent).on(nationStudent.nationStudentNum.eq(jobNationStudent.nationStudent.nationStudentNum))
                .join(job).on(jobNationStudent.job.jobNum.eq(job.jobNum))
                .where(nationStudent.nation.nationNum.eq(studentjobDto.getNationNum()).and(nationStudent.state.eq(NationStateType.IN_NATION)))
                .fetch();
        return studentJobList;
    }

    /**
     * 직업부여수정
     */
    public void studentJobUpdate(RequestStudentJobDto studentjobDto) {
        Job job =jobRepository.findById(studentjobDto.getJobNum()).orElse(null);
        NationStudent nationStudent = nationStudentRepository.findById(studentjobDto.getNationStudentNum()).orElse(null);

        JobNationStudent jobNationStudent = JobNationStudent.toEntity(job,nationStudent,studentjobDto);
        jobNationStudentRepository.save(jobNationStudent);

    }
}
