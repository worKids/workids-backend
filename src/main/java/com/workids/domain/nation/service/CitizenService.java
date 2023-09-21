package com.workids.domain.nation.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.workids.domain.nation.dto.request.RequestCitizenJoinDto;
import com.workids.domain.nation.dto.request.RequestCitizenUpdateDto;
import com.workids.domain.nation.dto.request.RequestNumDto;
import com.workids.domain.nation.dto.response.ResponseNationCitizenDto;
import com.workids.domain.nation.dto.response.ResponseTeacherNationListDto;
import com.workids.domain.nation.entity.Citizen;
import com.workids.domain.nation.entity.Nation;
import com.workids.domain.nation.repository.CitizenRepository;
import com.workids.domain.nation.repository.NationRepository;
import com.workids.global.exception.ApiException;
import com.workids.global.exception.ExceptionEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.workids.domain.nation.entity.QCitizen.citizen;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CitizenService {

    private final CitizenRepository citizenRepository;
    private final NationRepository nationRepository;

    private final JPAQueryFactory queryFactory;

    /**
     * 국민목록 조회
     */
    @Transactional
    public List<ResponseNationCitizenDto> getCitizen(RequestNumDto dto){

        List<Citizen> list = citizenRepository.findByNation_NationNum(dto.getNum());


        List<ResponseNationCitizenDto> dtoList = new ArrayList<>();
        for(Citizen citizen : list){
            dtoList.add(ResponseNationCitizenDto.toDto(citizen));
        }

        return dtoList;
    }

    /**
     * 국민목록 등록
     */
    @Transactional
    public void join(RequestCitizenJoinDto dto) {

        Nation nation = nationRepository.findById(dto.getNationNum()).orElse(null);

        // 나라 고유번호 존재하면 가입 가능
        if (nation == null){
            throw new ApiException(ExceptionEnum.NATION_NOT_EXIST_EXCEPTION);
        }


        // 학급번호 중복 불가
        if(citizenRepository.findByCitizenNumberAndNation_NationNum(dto.getCitizenNumber(),
                dto.getNationNum()) != null){
            throw new ApiException(ExceptionEnum.CITIZEN_NOT_JOIN_EXCEPTION);
        }
        // 국민목록 등록
        citizenRepository.save(Citizen.of(dto, nation));



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

    /**
     * 국민목록 수정
     */
    @Transactional
    public void update(List<RequestCitizenUpdateDto> dtoList){


        // citizenNumber 중복여부 확인
        List<Integer> citizenNumberArr = new ArrayList<>();
        for(RequestCitizenUpdateDto dto : dtoList) {
            if(citizenNumberArr.contains(dto.getCitizenNumber())){
                throw new ApiException(ExceptionEnum.CITIZEN_NOT_JOIN_EXCEPTION);
            }else{
                citizenNumberArr.add(dto.getCitizenNumber());
            }
        }

        for(RequestCitizenUpdateDto dto : dtoList) {
            // 국민목록 찾기
            Citizen citizen = citizenRepository.findByCitizenNumAndNation_NationNum(dto.getCitizenNum(), dto.getNationNum());
            if (citizen == null) {
                throw new ApiException(ExceptionEnum.NATION_NOT_JOIN_EXCEPTION);
            }
            citizen.updateState(dto);
        }

        System.out.println("국민목록 수정 완료");

    }

    /**
     * 국민목록 삭제
     */
    @Transactional
    public void delete(RequestNumDto dto) {

        // 국민목록 찾기
        Citizen citizen = citizenRepository.findByCitizenNum(dto.getNum());

        citizenRepository.delete(citizen);

        System.out.println("국민목록에서 해당 국민 삭제 완료");

    }
}
