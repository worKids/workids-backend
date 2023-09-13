package com.workids.domain.consumption.repository;

import com.workids.domain.consumption.entity.ConsumptionNationStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ConsumptionNationStudentRepository extends JpaRepository<ConsumptionNationStudent, Long>, QuerydslPredicateExecutor<ConsumptionNationStudent> {

}
