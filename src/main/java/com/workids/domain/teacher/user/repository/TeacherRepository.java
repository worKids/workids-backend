package com.workids.domain.teacher.user.repository;

import com.workids.domain.teacher.user.dto.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long>{

}
