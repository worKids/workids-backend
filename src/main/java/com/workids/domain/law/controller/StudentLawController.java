package com.workids.domain.law.controller;

import com.workids.domain.law.dto.request.RequestLawDto;
import com.workids.domain.law.dto.request.RequestLawNationStudentDto;
import com.workids.domain.law.dto.response.ResponseLawDto;
import com.workids.domain.law.dto.response.ResponseLawNationStudentDto;
import com.workids.domain.law.service.LawService;
import com.workids.global.comm.BaseResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class StudentLawController {

    @Autowired
    private LawService lawService;

    /**
     * 학생에게 부여된 벌금 내역 조회
     * */
    @PostMapping("student/law/fine/list")
    public ResponseEntity<BaseResponseDto<?>> getStudentFineLaws(@RequestBody RequestLawNationStudentDto dto){

        List<ResponseLawNationStudentDto> list = lawService.getStudentFineLaws(dto);

        for(ResponseLawNationStudentDto fineDto : list) {
            System.out.println(fineDto.toString());
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success"));
    }

    /**
     * 학생에게 부여된 벌칙 내역 조회
     * */
    @PostMapping("student/law/penalty/list")
    public ResponseEntity<BaseResponseDto<?>> getStudentPenaltyLaws(@RequestBody RequestLawNationStudentDto dto){

        List<ResponseLawNationStudentDto> list = lawService.getStudentPenaltyLaws(dto);

        for(ResponseLawNationStudentDto penaltyDto : list) {
            System.out.println(penaltyDto.toString());
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success"));
    }
}
