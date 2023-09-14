package com.workids.domain.bank.repository;

import com.workids.domain.bank.entity.BankNationStudent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankNationStudentRepository extends JpaRepository<BankNationStudent, Long> {

    BankNationStudent findByAccountNumber(String accountNumber);
}
