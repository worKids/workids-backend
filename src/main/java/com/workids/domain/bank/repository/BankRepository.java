package com.workids.domain.bank.repository;

import com.workids.domain.bank.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface BankRepository extends JpaRepository<Bank, Long>, QuerydslPredicateExecutor<Bank>{

}
