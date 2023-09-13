package com.workids.domain.consumption.controller;

import com.workids.domain.consumption.dto.request.RequestConsumptionNationStudentDto;
import com.workids.domain.consumption.dto.response.ResponseConsumptionNationStudentDto;
import com.workids.domain.consumption.service.StudentConsumptionService;
import com.workids.global.comm.BaseResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class StudentConsumptionController {

    @Autowired
    private StudentConsumptionService consumptionService;

    /**
     * 소비 신청
     * */
    @PostMapping("student/consumption")
    public ResponseEntity<BaseResponseDto<?>> createStudentConsumption(@RequestBody RequestConsumptionNationStudentDto dto){
        //dto => consumptionNum, nationStudentNum 필요
        consumptionService.createStudentConsumption(dto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success"));
    }

    /**
     * 내 소비 신청 내역 조회(대기, 취소, 승인, 거절)
     * */
    @PostMapping("student/consumption/list")
    public ResponseEntity<BaseResponseDto<List<ResponseConsumptionNationStudentDto>>> getStudentConsumptions(@RequestBody RequestConsumptionNationStudentDto dto){
        //dto => nationStudentNum 필요
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
    @PatchMapping("student/consumption/cancel")
    public ResponseEntity<BaseResponseDto<?>> updateConsumptionNationStudentStateByStudent(@RequestBody RequestConsumptionNationStudentDto dto){
        //dto => consumptionNationStudentNum 필요
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
    @PostMapping("student/consumption/complete/list")
    public ResponseEntity<BaseResponseDto<List<ResponseConsumptionNationStudentDto>>> getCompleteStudentConsumptions(@RequestBody RequestConsumptionNationStudentDto dto){
        //dto => nationStudentNum 필요
        List<ResponseConsumptionNationStudentDto>  list = consumptionService.getCompleteStudentConsumptions(dto);

        for(ResponseConsumptionNationStudentDto studentConsumptionDto : list) {
            System.out.println(studentConsumptionDto.toString());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success",list));
    }
}
