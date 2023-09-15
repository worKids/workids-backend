package com.workids.domain.nation.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.workids.domain.bank.dto.response.ResponseStudentBankDto;
import com.workids.domain.bank.entity.Bank;
import com.workids.domain.nation.dto.request.RequestCitizenJoinDto;
import com.workids.domain.nation.entity.Citizen;
import com.workids.domain.nation.entity.Nation;
import com.workids.domain.nation.repository.CitizenRepository;
import com.workids.domain.nation.repository.NationRepository;
import com.workids.global.config.stateType.BankStateType;
import com.workids.global.exception.ApiException;
import com.workids.global.exception.ExceptionEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.workids.domain.bank.entity.QBank.bank;
import static com.workids.domain.nation.entity.QCitizen.citizen;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CitizenService {

    private final CitizenRepository citizenRepository;
    private final NationRepository nationRepository;

    private final JPAQueryFactory queryFactory;

    /**
     * 국민목록 등록
     */
    @Transactional
    public void join(List<RequestCitizenJoinDto> dtoList) {

        Nation nation = nationRepository.findById(dtoList.get(dtoList.size()-1).getNationNum()).orElse(null);

        // 나라 고유번호 존재하면 가입 가능
        if (nation == null){
            throw new ApiException(ExceptionEnum.NATION_NOT_EXIST_EXCEPTION);
        }


        for(RequestCitizenJoinDto dto : dtoList){
            // 학급번호 중복 불가
            if(citizenRepository.findByCitizenNumberAndNation_NationNum(dto.getCitizenNumber(),
                    dto.getNationNum()) != null){
                throw new ApiException(ExceptionEnum.CITIZEN_NOT_JOIN_EXCEPTION);
            }
            // 국민목록 등록
            citizenRepository.save(Citizen.of(dto, nation));
        }


        System.out.println("국민목록 citizen 저장 완료.");

    }

    /**
     * 국민 수 구하기
     */
    @Transactional
    public int citizenCount(Long nationNum){
        // 모든 항목 조회(사용중)-상품 유형으로 정렬
        // Entity 리스트로 결과
        List<Citizen> citizenList = queryFactory.selectFrom(citizen)
                .where(citizen.nation.nationNum.eq(nationNum))
                .fetch();

        int count = citizenList.size();

        return count;
    }
}
