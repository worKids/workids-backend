package com.workids.domain.job.repository;


import com.workids.domain.job.dto.request.RequestStudentJobDto;
import com.workids.domain.job.dto.response.ResponseJobKindDto;
import com.workids.domain.job.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {


    /**
     * 직업삭제
     */
    @Query(value =  "UPDATE JOB SET STATE = 1 WHERE NAME = ?1 AND NATION_NUM = ?2", nativeQuery = true)
    @Modifying
    void delete(String jobName, Long nationNum);

    Job findByNation_NationNumAndName(Long nationNum, String name);


    Job findByNation_NationNum(Long nationNum);

}
