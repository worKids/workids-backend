package com.workids.domain.consumption.controller;

import com.workids.domain.consumption.dto.request.RequestConsumptionDto;
import com.workids.domain.consumption.dto.request.RequestConsumptionNationStudentDto;
import com.workids.domain.consumption.dto.response.ResponseConsumptionDto;
import com.workids.domain.consumption.dto.response.ResponseConsumptionNationStudentDto;
import com.workids.domain.consumption.service.TeacherConsumptionService;
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
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TeacherConsumptionController {

    @Autowired
    private TeacherConsumptionService consumptionService;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 소비 사용 중인 항목 조회
     * */
    @PostMapping("/consumption/list")
    @ResponseBody
    public ResponseEntity<BaseResponseDto<List<ResponseConsumptionDto>>> getAllConsumptions(HttpServletRequest request, @RequestBody RequestConsumptionDto dto){
        //dto -> nationNum 필요

        if (!jwtTokenProvider.validateToken(request.getHeader("Authorization"))) {
            throw new ApiException(ExceptionEnum.MEMBER_ACCESS_EXCEPTION);
        };

        List<ResponseConsumptionDto> list = consumptionService.getAllConsumptions(dto);

        for (ResponseConsumptionDto consumptionDtoDto : list) {
            System.out.println(consumptionDtoDto.toString());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success",consumptionService.getAllConsumptions(dto)));

    }

    /**
     * 소비 항목 추가
     * */
    @PostMapping("/teacher/consumption")
    public ResponseEntity<BaseResponseDto<?>> createConsumption(HttpServletRequest request, @RequestBody RequestConsumptionDto dto){
        //dto => nationNum, content, amount 필요
        
        if (!jwtTokenProvider.validateToken(request.getHeader("Authorization"))) {
            throw new ApiException(ExceptionEnum.MEMBER_ACCESS_EXCEPTION);
        };

        System.out.println("삽입할 소비 항목 "+ dto);
        consumptionService.createConsumption(dto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success"));
    }

    /**
     * 소비 항목 수정(금액만 수정 가능)
     * */
    @PatchMapping("/teacher/consumption")
    public ResponseEntity<BaseResponseDto<?>> updateConsumption(HttpServletRequest request, @RequestBody RequestConsumptionDto dto){
        //dto =>nationNum, consumptionNum, content, amount 필요

        if (!jwtTokenProvider.validateToken(request.getHeader("Authorization"))) {
            throw new ApiException(ExceptionEnum.MEMBER_ACCESS_EXCEPTION);
        };

        long result = consumptionService.updateConsumption(dto);

        if(result!=0){
            System.out.println("수정 완료");
        }else{
            System.out.println("수정 실패");
        }
        
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success"));
    }

    /**
     * 소비 항목 삭제
     * */
    @PatchMapping("/teacher/consumption/hide")
    public ResponseEntity<BaseResponseDto<?>> updateConsumptionState(HttpServletRequest request, @RequestBody RequestConsumptionDto dto){
        //dto =>consumptionNum 필요

        if (!jwtTokenProvider.validateToken(request.getHeader("Authorization"))) {
            throw new ApiException(ExceptionEnum.MEMBER_ACCESS_EXCEPTION);
        };

        long result = consumptionService.updateConsumptionState(dto);
        if(result!=0){
            System.out.println("삭제 완료");
        }else{
            System.out.println("삭제 실패");
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success"));
    }

    /**
     * 소비 신청 항목(미결재)조회
     * */
    @PostMapping("/teacher/consumption/citizen/outstanding/list")
    @ResponseBody
    public ResponseEntity<BaseResponseDto<List<ResponseConsumptionNationStudentDto>>> getOutStandingConsumptions(HttpServletRequest request, @RequestBody RequestConsumptionNationStudentDto dto){
        //dto =>nationNum 필요

        if (!jwtTokenProvider.validateToken(request.getHeader("Authorization"))) {
            throw new ApiException(ExceptionEnum.MEMBER_ACCESS_EXCEPTION);
        };

        List<ResponseConsumptionNationStudentDto> list = consumptionService.getOutStandingConsumptions(dto);

        for(ResponseConsumptionNationStudentDto consumptionNationStudentDto : list) {
            System.out.println(consumptionNationStudentDto.toString());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success",list));

    }

    /**
     * 소비 신청 항목(결재)조회
     * */
    @PostMapping("/teacher/consumption/citizen/approval/list")
    @ResponseBody
    public ResponseEntity<BaseResponseDto<List<ResponseConsumptionNationStudentDto>>> getApprovalConsumptions(HttpServletRequest request, @RequestBody RequestConsumptionNationStudentDto dto){
        //dto =>nationNum 필요

        if (!jwtTokenProvider.validateToken(request.getHeader("Authorization"))) {
            throw new ApiException(ExceptionEnum.MEMBER_ACCESS_EXCEPTION);
        };

        List<ResponseConsumptionNationStudentDto> list = consumptionService.getApprovalConsumptions(dto);

        for(ResponseConsumptionNationStudentDto consumptionNationStudentDto : list) {
            System.out.println(consumptionNationStudentDto.toString());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success",list));

    }

    /**
     * 소비 신청 미결재 처리
     * */
    @PostMapping("/teacher/consumption/citizen/outstanding/process")
    public ResponseEntity<BaseResponseDto<?>> updateConsumptionNationStudentStateByTeacher(HttpServletRequest request, @RequestBody RequestConsumptionNationStudentDto dto){
        //dto =>consumptionNationStudentNum, state 필요

        if (!jwtTokenProvider.validateToken(request.getHeader("Authorization"))) {
            throw new ApiException(ExceptionEnum.MEMBER_ACCESS_EXCEPTION);
        };

        long result = consumptionService.updateConsumptionNationStudentStateByTeacher(dto);
        if(result!=0){
            System.out.println("처리 완료");
        }else{
            System.out.println("처리 실패");
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success"));
    }
}
