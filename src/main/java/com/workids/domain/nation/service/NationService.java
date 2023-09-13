package com.workids.domain.nation.service;

import com.workids.domain.nation.dto.request.RequestNationInfoDto;
import com.workids.domain.nation.dto.request.RequestNationJoinDto;
import com.workids.domain.nation.dto.request.RequestNationListDto;
import com.workids.domain.nation.dto.response.ResponseNationInfoDto;
import com.workids.domain.nation.dto.response.ResponseStudentNationListDto;
import com.workids.domain.nation.dto.response.ResponseTeacherNationListDto;
import com.workids.domain.nation.entity.Nation;
import com.workids.domain.nation.entity.NationStudent;
import com.workids.domain.nation.repository.NationRepository;
import com.workids.domain.nation.repository.NationStudentRepository;
import com.workids.domain.user.entity.Teacher;
import com.workids.domain.user.repository.TeacherRepository;
import com.workids.global.exception.ApiException;
import com.workids.global.exception.ExceptionEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NationService {


    private final NationRepository nationRepository;
    private final TeacherRepository teacherRepository;
    private final NationStudentRepository nationStudentRepository;

    /**
     * 나라 생성
     */
    @Transactional
    public void join(RequestNationJoinDto dto, String code) {

        if (nationRepository.findByName(dto.getName()) != null){
            //NATION_EXIST_EXCEPTION(HttpStatus.CONFLICT, "N0002","중복된 나라 이름입니다");
            throw new IllegalArgumentException("이미 존재하는 나라이름입니다.");
        }

        // 날짜 타입 변환
        LocalDateTime[] times = toLocalDateTime(dto.getStartDate(), dto.getEndDate());

        Teacher teacher = teacherRepository.findByTeacherNum(dto.getTeacherNum());

        nationRepository.save(Nation.of(dto, teacher, times[0], times[1], code));

    }

    /**
     * teacher과 연결된 나라 전체 조회
     */
    @Transactional
    public List<ResponseTeacherNationListDto> getTeacherList(RequestNationListDto dto){

        List<Nation> list = nationRepository.findByTeacher_TeacherNum(dto.getNum());

        if(list.size() == 0)
            throw new ApiException(ExceptionEnum.TEACHER_NOT_MATCH_EXCEPTION);

        int totalTeacher = list.size();
        System.out.println("teacher size" + totalTeacher);

        List<ResponseTeacherNationListDto> dtoList = new ArrayList<>();
        for(Nation nation : list){
            dtoList.add(ResponseTeacherNationListDto.of(nation, totalTeacher));
        }

        return dtoList;
    }

    /**
     * student와 연결된 나라 전체 조회
     */
    @Transactional
    public List<ResponseStudentNationListDto> getStudentNationList(RequestNationListDto dto){

        List<NationStudent> list = nationStudentRepository.findByStudent_StudentNum(dto.getNum());

        if(list.size() == 0)
            throw new ApiException(ExceptionEnum.TEACHER_NOT_MATCH_EXCEPTION);

        int totalStudent = list.size();
        System.out.println("size: " + totalStudent);


        List<ResponseStudentNationListDto> dtoList = new ArrayList<>();
        for(NationStudent nationstudent : list){
            dtoList.add(ResponseStudentNationListDto.of(nationstudent, totalStudent));
        }

        return dtoList;
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

    /**
     * 나라 참여코드 생성
     */
    public String randomCode() {
        Random random = new Random();
        int randomStrLen = 8;
        StringBuffer randomBuf = new StringBuffer();
        for (int i = 0; i < randomStrLen; i++) {
            if (random.nextBoolean()) {
                randomBuf.append((char) ((int) (random.nextInt(26)) + 97));
            } else {
                randomBuf.append(random.nextInt(10));
            }
        }
        String randomStr = randomBuf.toString().toUpperCase();

        return randomStr;
    }
    /**
     * 날짜 데이터타입 변환
     * : String -> LocalDatetime
     */
    public LocalDateTime[] toLocalDateTime(String start, String end){

        String startDate = start+ " 00:00:00.000";
        String endDate = end + " 11:59:59.999";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

        LocalDateTime startDateTime = LocalDateTime.parse(startDate, formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(endDate, formatter);

        LocalDateTime[] times = new LocalDateTime[]{startDateTime, endDateTime};

        return times;

    }



}
