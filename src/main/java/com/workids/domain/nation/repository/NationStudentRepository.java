package com.workids.domain.nation.repository;

import com.workids.domain.nation.entity.Nation;
import com.workids.domain.nation.entity.NationStudent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NationStudentRepository extends JpaRepository<NationStudent, Long>{


    Nation findByNation_NationNum(Long nationNum);

    NationStudent findByCitizenNumber(int citizenNumber);

    List<NationStudent> findByStudent_StudentNum(Long studentNum);
}
