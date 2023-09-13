package com.workids.domain.law.controller;

import com.workids.domain.law.dto.request.RequestLawNationStudentDto;
import com.workids.domain.law.dto.response.ResponseLawNationStudentDto;
import com.workids.domain.law.service.StudentLawService;
import com.workids.global.comm.BaseResponseDto;
import com.workids.global.exception.ApiException;
import com.workids.global.exception.ExceptionEnum;
import com.workids.global.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class StudentLawController {

    @Autowired
    private StudentLawService lawService;

    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 학생에게 부여된 벌금 내역 조회
     * */
    @PostMapping("/student/law/fine/list")
    public ResponseEntity<BaseResponseDto<List<ResponseLawNationStudentDto>>> getStudentFineLaws(HttpServletRequest request,  @RequestBody RequestLawNationStudentDto dto){
        //dto => nationStudentNum 필요

        if (!jwtTokenProvider.validateToken(request.getHeader("Authorization"))) {
            throw new ApiException(ExceptionEnum.MEMBER_ACCESS_EXCEPTION);
        };

        List<ResponseLawNationStudentDto> list = lawService.getStudentFineLaws(dto);

        for(ResponseLawNationStudentDto fineDto : list) {
            System.out.println(fineDto.toString());
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success",list));
    }

    /**
     * 학생에게 부여된 벌칙 내역 조회
     * */
    @PostMapping("/student/law/penalty/list")
    public ResponseEntity<BaseResponseDto<List<ResponseLawNationStudentDto>>> getStudentPenaltyLaws(HttpServletRequest request, @RequestBody RequestLawNationStudentDto dto){
        //dto => nationStudentNum 필요

        if (!jwtTokenProvider.validateToken(request.getHeader("Authorization"))) {
            throw new ApiException(ExceptionEnum.MEMBER_ACCESS_EXCEPTION);
        };

        List<ResponseLawNationStudentDto> list = lawService.getStudentPenaltyLaws(dto);

        for(ResponseLawNationStudentDto penaltyDto : list) {
            System.out.println(penaltyDto.toString());
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success",list));
    }
}
