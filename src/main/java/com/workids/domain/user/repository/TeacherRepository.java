package com.workids.domain.user.repository;

import com.workids.domain.user.entity.Student;
import com.workids.domain.user.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long>{


    Optional<Teacher> findById(String id);

    Optional<Teacher> findByEmail(String email);

}
