package com.workids.bankTest;

import com.workids.domain.bank.entity.Bank;
import com.workids.domain.bank.repository.BankRepository;
import com.workids.domain.nation.entity.Nation;
import com.workids.domain.nation.repository.NationRepository;
import com.workids.global.config.stateType.BankStateType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Commit
@Transactional
public class BankTest {
    @Autowired
    BankRepository bankRepository;

    @Autowired
    NationRepository nationRepository;

    /**
     * 은행 상품 등록
     */
    @Test
    void bankInsert(){
        Nation nation = nationRepository.findById(1L).orElse(null);

        Bank bank = Bank.builder()
                .nation(nation)
                .productType(BankStateType.MAIN_ACCOUNT)
                .productName("주거래 상품")
                .productContent("주거래 상품입니다.")
                .productPeriod(0)
                .interestRate(0)
                .cancelInterestRate(0)
                .productState(BankStateType.IN_USE)
                .build();

        bankRepository.save(bank);

        bank = Bank.builder()
                .nation(nation)
                .productType(BankStateType.DEPOSIT_ACCOUNT)
                .productName("예금 상품1")
                .productContent("예금 상품1입니다.")
                .productPeriod(3)
                .interestRate(3)
                .cancelInterestRate(1.5)
                .productState(BankStateType.IN_USE)
                .build();

        bankRepository.save(bank);

        bank = Bank.builder()
                .nation(nation)
                .productType(BankStateType.DEPOSIT_ACCOUNT)
                .productName("예금 상품2")
                .productContent("예금 상품2입니다.")
                .productPeriod(4)
                .interestRate(4)
                .cancelInterestRate(2)
                .productState(BankStateType.IN_USE)
                .build();

        bankRepository.save(bank);

        bank = Bank.builder()
                .nation(nation)
                .productType(BankStateType.DEPOSIT_ACCOUNT)
                .productName("예금 상품3")
                .productContent("예금 상품3입니다.")
                .productPeriod(1)
                .interestRate(2)
                .cancelInterestRate(1)
                .productState(BankStateType.IN_USE)
                .build();

        bankRepository.save(bank);
    }
}
