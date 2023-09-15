package com.workids.domain.user.repository;

import com.workids.domain.user.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long>{

    Optional<Teacher> findById(String id);

    Optional<Teacher> findByEmail(String email);

    Teacher findByTeacherNum(Long teacherNum);
}
