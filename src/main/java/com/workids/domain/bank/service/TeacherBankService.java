package com.workids.domain.bank.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.workids.domain.bank.dto.request.RequestBankTeacherCreateDto;
import com.workids.domain.bank.dto.response.ResponseBankTeacherListDto;
import com.workids.domain.bank.entity.Bank;
import com.workids.domain.bank.repository.BankRepository;
import com.workids.domain.nation.entity.Nation;
import com.workids.domain.nation.repository.NationRepository;
import com.workids.global.config.stateType.BankStateType;
import com.workids.global.exception.ApiException;
import com.workids.global.exception.ExceptionEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static com.workids.domain.bank.entity.QBank.bank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Teacher 은행 Service
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeacherBankService {
    private final BankRepository bankRepository;
    private final NationRepository nationRepository;
    private final JPAQueryFactory queryFactory;

    /**
     * 은행 상품 등록
     */
    @Transactional
    public void createBank(RequestBankTeacherCreateDto dto){
        // 나라 정보 조회
        Nation nation = nationRepository.findById(dto.getNationNum()).orElse(null);
        
        // 은행 상품 정보 생성
        Bank newBank = Bank.of(dto, nation, BankStateType.IN_USE);
        
        // 은행 상품 등록
        Bank bankResult = bankRepository.save(newBank);
    }

    /**
     * 전체 은행 상품 조회(현재 사용중, 미사용중 모두 조회)
     */
    @Transactional
    public List<ResponseBankTeacherListDto> getBankList(Long nationNum){
        // 전체 항목 조회(사용중, 미사용중)-상품 유형, 상품 항목 상태, 상품 고유 번호로 정렬
        // Entity 리스트로 결과
        List<Bank> bankProductList = queryFactory.selectFrom(bank)
                        .where(bank.nation.nationNum.eq(nationNum))
                        .orderBy(bank.productType.asc(), bank.productState.asc(), bank.productNum.asc())
                        .fetch();

        // Dto 리스트로 변환
        List<ResponseBankTeacherListDto> resultList = new ArrayList<>();
        bankProductList.forEach(b-> {
            System.out.println(b); // 결과 확인
            resultList.add(ResponseBankTeacherListDto.toDto(b));
        });

        return resultList;
    }

    /**
     * 은행 상품 삭제
     */
    @Transactional
    public void updateBankState(Long productNum) throws ApiException{
        // 은행 상품 존재하는지 확인
        Bank bank = bankRepository.findById(productNum).orElseThrow(() -> new ApiException(ExceptionEnum.BANK_NOT_EXIST_EXCEPTION));

        // 현재 날짜 시간-얻기
        LocalDateTime now = LocalDateTime.now();

        // 존재하면 상태, 종료일 업데이트
        bank.updateState(BankStateType.UN_USE, now);
    }
}
