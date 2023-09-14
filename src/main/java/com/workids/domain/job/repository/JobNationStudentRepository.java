package com.workids.domain.job.repository;

import com.workids.domain.job.entity.JobNationStudent;
import com.workids.domain.nation.entity.NationStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface JobNationStudentRepository extends JpaRepository<JobNationStudent, Long> {

    /**
     * 직업부여 수정
     */
    @Query(value =  "UPDATE JOB_NATION_STUDENT SET JOB_NUM = ?2 WHERE NATION_STUDENT_NUM = ?1", nativeQuery = true)
    @Modifying
    void update(Long nationStudentNum, Long jobNum);
}
