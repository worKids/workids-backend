package com.workids.domain.nation.service;

import com.workids.domain.nation.dto.request.RequestNationInfoDto;
import com.workids.domain.nation.dto.request.RequestNationJoinDto;
import com.workids.domain.nation.dto.request.RequestNationListAllDto;
import com.workids.domain.nation.dto.response.ResponseNationInfoDto;
import com.workids.domain.nation.dto.response.ResponseNationListAllDto;
import com.workids.domain.nation.entity.Nation;
import com.workids.domain.nation.repository.NationRepository;
import com.workids.domain.user.entity.Teacher;
import com.workids.domain.user.repository.TeacherRepository;
import com.workids.global.exception.ApiException;
import com.workids.global.exception.ApiExceptionEntity;
import com.workids.global.exception.ExceptionEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.workids.global.exception.ExceptionEnum.NATION_NOT_EXIST_EXCEPTION;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NationService {


    private final NationRepository nationRepository;
    private final TeacherRepository teacherRepository;

    @Transactional
    public List<ResponseNationListAllDto> getListAll(RequestNationListAllDto dto){

        List<Nation> list = nationRepository.findByTeacher_TeacherNum(dto.getTeacherNum());

        if(list.size() == 0)
            throw new ApiException(ExceptionEnum.TEACHER_NOT_MATCH_EXCEPTION);


        List<ResponseNationListAllDto> dtoList = new ArrayList<>();
        for(Nation nation : list){
            dtoList.add(ResponseNationListAllDto.of(nation));
        }

        return dtoList;
    }

    @Transactional
    public void join(RequestNationJoinDto dto) {

        if (nationRepository.findByName(dto.getName()) != null){
            //NATION_EXIST_EXCEPTION(HttpStatus.CONFLICT, "N0002","중복된 나라 이름입니다");
            throw new IllegalArgumentException("이미 존재하는 나라이름입니다.");
        }

        Teacher teacher = teacherRepository.findByTeacherNum(dto.getTeacherNum());

        nationRepository.save(Nation.of(dto, teacher));

    }

    /**
     * 나라 고유번호로 select
     */
    @Transactional
    public ResponseNationInfoDto getInfo(RequestNationInfoDto dto){
        Long nationNum = dto.getNationNum();
        Optional<Nation> optNation = nationRepository.findById(nationNum);

        Nation nation;
        ResponseNationInfoDto infoDto = null;
        if(optNation.isPresent()){
            nation = optNation.orElseThrow(NullPointerException::new);
            infoDto = ResponseNationInfoDto.toDto(nation);

        }

        return infoDto;


    }

}
