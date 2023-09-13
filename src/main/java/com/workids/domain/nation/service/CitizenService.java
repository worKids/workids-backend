package com.workids.domain.nation.service;

import com.workids.domain.nation.dto.request.RequestCitizenJoinDto;
import com.workids.domain.nation.entity.Citizen;
import com.workids.domain.nation.entity.Nation;
import com.workids.domain.nation.repository.CitizenRepository;
import com.workids.domain.nation.repository.NationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CitizenService {

    private final CitizenRepository citizenRepository;
    private final NationRepository nationRepository;

    /**
     * 국민목록 등록
     */
    @Transactional
    public void join(List<RequestCitizenJoinDto> dtoList) {

        Nation nation = nationRepository.findById(dtoList.get(dtoList.size()-1).getNationNum()).orElse(null);

        // 나라 고유번호 존재하면 가입 가능
        if (nation == null){
            //NATION_EXIST_EXCEPTION(HttpStatus.CONFLICT, "N0002","중복된 나라 이름입니다");
            throw new IllegalArgumentException("나라가 존재하지 않습니다.");
        }

        for(RequestCitizenJoinDto dto : dtoList){
            citizenRepository.save(Citizen.of(dto, nation));
        }


        System.out.println("국민목록 citizen 저장 완료.");

    }

    /**
     * 국민 수 구하기
     */
}
