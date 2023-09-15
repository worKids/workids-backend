package com.workids.domain.bank.repository;

import com.workids.domain.bank.entity.Bank;
import com.workids.domain.bank.entity.BankNationStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface BankNationStudentRepository extends JpaRepository<BankNationStudent, Long>, QuerydslPredicateExecutor<BankNationStudent> {

    BankNationStudent findByAccountNumber(String accountNumber);

    BankNationStudent findByNationStudent_NationStudentNumAndBank_ProductType(Long studentNum, int type);

}
