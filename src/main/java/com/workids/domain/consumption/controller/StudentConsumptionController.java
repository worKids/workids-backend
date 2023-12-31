package com.workids.domain.consumption.controller;

import com.workids.domain.consumption.dto.request.RequestConsumptionNationStudentDto;
import com.workids.domain.consumption.dto.response.ResponseConsumptionNationStudentDto;
import com.workids.domain.consumption.service.StudentConsumptionService;
import com.workids.global.comm.BaseResponseDto;
import com.workids.global.exception.ApiException;
import com.workids.global.exception.ExceptionEnum;
import com.workids.global.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class StudentConsumptionController {

    @Autowired
    private StudentConsumptionService consumptionService;

    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 소비 신청
     * */
    @PostMapping("/student/consumption")
    public ResponseEntity<BaseResponseDto<?>> createStudentConsumption(HttpServletRequest request, @RequestBody RequestConsumptionNationStudentDto dto){
        //dto => consumptionNum, nationStudentNum 필요

        if (!jwtTokenProvider.validateToken(request.getHeader("Authorization"))) {
            throw new ApiException(ExceptionEnum.MEMBER_ACCESS_EXCEPTION);
        };

        consumptionService.createStudentConsumption(dto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success"));
    }

    /**
     * 내 소비 신청 내역 조회(대기, 취소, 승인, 거절)
     * */
    @PostMapping("/student/consumption/list")
    public ResponseEntity<BaseResponseDto<List<ResponseConsumptionNationStudentDto>>> getStudentConsumptions(HttpServletRequest request, @RequestBody RequestConsumptionNationStudentDto dto){
        //dto => nationStudentNum 필요

        if (!jwtTokenProvider.validateToken(request.getHeader("Authorization"))) {
            throw new ApiException(ExceptionEnum.MEMBER_ACCESS_EXCEPTION);
        };

        List<ResponseConsumptionNationStudentDto>  list = consumptionService.getStudentConsumptions(dto);

        for(ResponseConsumptionNationStudentDto studentConsumptionDto : list) {
            System.out.println(studentConsumptionDto.toString());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success",list));
    }

    /**
     * 신청한 내역 취소하기 (대기 중인 것만 가능)
     * */
    @PatchMapping("/student/consumption/cancel")
    public ResponseEntity<BaseResponseDto<?>> updateConsumptionNationStudentStateByStudent(HttpServletRequest request, @RequestBody RequestConsumptionNationStudentDto dto){
        //dto => consumptionNationStudentNum 필요

        if (!jwtTokenProvider.validateToken(request.getHeader("Authorization"))) {
            throw new ApiException(ExceptionEnum.MEMBER_ACCESS_EXCEPTION);
        };

        long result = consumptionService.updateConsumptionNationStudentStateByStudent(dto);

        if(result!=0){
            System.out.println("처리 완료");
        }else{
            System.out.println("처리 실패");
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success"));
    }

    /**
     * 소비 승인 완료 내역 조회(승인)
     * */
    @PostMapping("/student/consumption/complete/list")
    public ResponseEntity<BaseResponseDto<List<ResponseConsumptionNationStudentDto>>> getCompleteStudentConsumptions(HttpServletRequest request, @RequestBody RequestConsumptionNationStudentDto dto){
        //dto => nationStudentNum 필요

        if (!jwtTokenProvider.validateToken(request.getHeader("Authorization"))) {
            throw new ApiException(ExceptionEnum.MEMBER_ACCESS_EXCEPTION);
        };

        List<ResponseConsumptionNationStudentDto>  list = consumptionService.getCompleteStudentConsumptions(dto);

        for(ResponseConsumptionNationStudentDto studentConsumptionDto : list) {
            System.out.println(studentConsumptionDto.toString());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success",list));
    }
}
