package com.workids.domain.nation.repository;

import com.workids.domain.bank.entity.Bank;
import com.workids.domain.nation.entity.Nation;
import com.workids.domain.nation.entity.NationStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

public interface NationStudentRepository extends JpaRepository<NationStudent, Long>, QuerydslPredicateExecutor<NationStudent> {


    Nation findByNation_NationNum(Long nationNum);

    NationStudent findByCitizenNumber(int citizenNumber);

    List<NationStudent> findByStudent_StudentNum(Long studentNum);
}
