package com.workids.domain.job.repository;

import com.workids.domain.job.entity.JobToDo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobToDoRepository extends JpaRepository<JobToDo, Long>{


}
