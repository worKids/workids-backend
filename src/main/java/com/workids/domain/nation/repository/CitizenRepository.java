package com.workids.domain.nation.repository;

import com.workids.domain.nation.entity.Citizen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface CitizenRepository extends JpaRepository<Citizen, Long>, QuerydslPredicateExecutor<Citizen> {

    List<Citizen> findByNation_NationNum(Long nationNum);

    Citizen findByCitizenNumber(int nationNumber);

}
