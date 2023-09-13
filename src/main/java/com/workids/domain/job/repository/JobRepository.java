package com.workids.domain.job.repository;


import com.workids.domain.job.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface JobRepository extends JpaRepository<Job, Long> {


    /**
     * 직업삭제
     */
    @Query(value =  "UPDATE JOB SET STATE = 1 WHERE JOB_NUM = ?1", nativeQuery = true)
    @Modifying
    void delete(Long jobNum);
}
