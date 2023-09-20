package com.workids.domain.bank.repository;

import com.workids.domain.bank.entity.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long>, QuerydslPredicateExecutor<TransactionHistory>{

    List<TransactionHistory> findAllByBankNationStudent_BankNationStudentNum(Long studentNum);
}
