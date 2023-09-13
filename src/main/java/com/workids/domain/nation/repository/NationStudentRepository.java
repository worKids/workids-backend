package com.workids.domain.nation.repository;

import com.workids.domain.nation.entity.Citizen;
import com.workids.domain.nation.entity.Nation;
import com.workids.domain.nation.entity.NationStudent;
import com.workids.domain.user.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

public interface NationStudentRepository extends JpaRepository<NationStudent, Long>{

    //Citizen findByCitizen_CitizenNumber(Long citizenNumber);

    Student findByStudent_StudentNum(Long studentNum);

    Nation findByNation_NationNum(Long nationNum);

    NationStudent findByCitizenNumber(int citizenNumber);
}
