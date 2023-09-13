package com.workids.domain.nation.repository;

import com.workids.domain.nation.entity.Nation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;


public interface NationRepository extends JpaRepository<Nation, Long>{

    Optional<Nation> findByNationNum(Long nationNum);

    Nation findByName(String name);

    List<Nation> findByTeacher_TeacherNum(Long teacherNum);



}