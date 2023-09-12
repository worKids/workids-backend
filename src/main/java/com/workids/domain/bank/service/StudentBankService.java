package com.workids.domain.bank.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.workids.domain.bank.dto.response.ResponseStudentBankDto;
import com.workids.domain.bank.entity.Bank;
import com.workids.domain.bank.repository.BankRepository;
import com.workids.global.config.stateType.BankStateType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.workids.domain.bank.entity.QBank.bank;

/**
 * Student 은행 Service
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StudentBankService {
    private final BankRepository bankRepository;

    private final JPAQueryFactory queryFactory;

    /**
     * 전체 은행 상품 조회(현재 사용중 모두 조회)
     */
    @Transactional
    public List<ResponseStudentBankDto> getBankList(Long nationNum){
        // 모든 항목 조회(사용중)-상품 유형으로 정렬
        // Entity 리스트로 결과
        List<Bank> bankProductList = queryFactory.selectFrom(bank)
                .where(bank.nation.nationNum.eq(nationNum),
                        bank.productState.eq(BankStateType.IN_USE))
                .orderBy(bank.productType.asc())
                .fetch();

        // Dto 리스트로 변환
        List<ResponseStudentBankDto> resultList = new ArrayList<>();
        bankProductList.forEach(b-> {
            System.out.println(b); // 결과 확인
            resultList.add(ResponseStudentBankDto.toDto(b));
        });

        return resultList;
    }

}
