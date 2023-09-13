package com.workids.domain.bank.repository;

import com.workids.domain.bank.entity.BankNationStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface BankNationStudentRepository extends JpaRepository<BankNationStudent, Long>, QuerydslPredicateExecutor<BankNationStudent>{

}
