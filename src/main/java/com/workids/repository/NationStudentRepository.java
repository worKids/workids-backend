package com.workids.repository;

import com.workids.domain.NationStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NationStudentRepository extends JpaRepository<NationStudent, Long>{

    @Query("select n from NationStudent n where n.citizenNumber=?1")
    NationStudent findByCitizenNumber(int citizen_number);
}
