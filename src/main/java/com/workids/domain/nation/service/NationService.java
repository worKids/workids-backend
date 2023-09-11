package com.workids.domain.nation.service;

import com.workids.domain.nation.dto.request.NationJoinDto;
import com.workids.domain.nation.dto.request.NationListALLDto;
import com.workids.domain.nation.dto.response.NationListResponseDto;
import com.workids.domain.nation.entity.Nation;
import com.workids.domain.nation.repository.NationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NationService {


    private final NationRepository nationRepository;

    @Transactional
    public List<NationListResponseDto> getListAll(Long teacherNum){
        List<Nation> list = nationRepository.findAllByTeacherId(teacherNum);

        List<NationListResponseDto> dtoList = new ArrayList<>();
        for(Nation nation : list){
            dtoList.add(NationListResponseDto.of(nation));
        }

        return dtoList;
    }

    @Transactional
    public void join(NationJoinDto dto) {

        if (nationRepository.findByName(dto.getName()) != null){
            throw new IllegalArgumentException("이미 존재하는 나라이름입니다.");
        }

        nationRepository.save(Nation.of(dto));

    }

}
