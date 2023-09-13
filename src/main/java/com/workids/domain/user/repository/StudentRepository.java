package com.workids.domain.user.repository;

import com.workids.domain.user.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Student findByStudentNum(Long studentNum);
    Optional<Student> findById(String id);

    Optional<Student> findByRegistNumber(String registNumber);

}
