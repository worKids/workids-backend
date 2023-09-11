package com.workids.controller;

import com.workids.dto.BaseResponseDto;
import com.workids.dto.request.RequestLawDto;
import com.workids.dto.request.RequestLawNationStudentDto;
import com.workids.dto.response.ResponseLawDto;
import com.workids.dto.response.ResponseLawNationStudentDto;
import com.workids.service.LawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class LawController {

    @Autowired
    private LawService lawService;

    /**
     * 법 조회
     * */
    @PostMapping("law/list")
    @ResponseBody
    public ResponseEntity<BaseResponseDto<?>> getAllLaws(@RequestBody RequestLawDto dto){

        List<ResponseLawDto> list = lawService.getAllLaws(dto);

        for(ResponseLawDto lawDto : list) {
            System.out.println(lawDto.toString());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success"));
    }

    /**
     *  법 제정(등록)
     * */
    @PostMapping("teacher/law")
    @ResponseBody
    public ResponseEntity<BaseResponseDto<?>> createLaw(@RequestBody RequestLawDto dto){

        System.out.println("삽입할 법 "+ dto);
        lawService.createLaw(dto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success"));
    }

    /**
     * 법 수정(벌금 가격만 가능)
     * */
    @PatchMapping("teacher/law")
    @ResponseBody
    public ResponseEntity<BaseResponseDto<?>> updateLaw(@RequestBody RequestLawDto dto){

        System.out.println("수정할 법 "+ dto);
        long result = lawService.updateLaw(dto);
        if(result!=0){
            System.out.println("수정 완료");
        }else{
            System.out.println("수정 실패");
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success"));
    }

    /**
     * 법 삭제
     * */
    @PatchMapping("teacher/law/hide")
    @ResponseBody
    public ResponseEntity<BaseResponseDto<?>> updateLawState(@RequestBody RequestLawDto dto){

        System.out.println("삭제할 법 "+ dto);
        long result = lawService.updateLawState(dto);
        if(result!=0){
            System.out.println("삭제 완료");
        }else{
            System.out.println("삭제 실패");
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success"));
    }
    /**
     * 벌금 부여 리스트
     */
    @PostMapping("teacher/law/fine/list")
    public ResponseEntity<BaseResponseDto<?>> getFineLaws(@RequestBody RequestLawNationStudentDto dto){
        List<ResponseLawNationStudentDto> list = lawService.getFineLaws(dto);

        for(ResponseLawNationStudentDto fineDto : list) {
            System.out.println(fineDto.toString());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success"));
    }

    /**
     * 벌금 부여
     * */
    @PostMapping("teacher/law/fine")
    public ResponseEntity<BaseResponseDto<?>> createFineStudent(@RequestBody RequestLawNationStudentDto dto){

        lawService.createFineStudent(dto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success"));
    }
    /**
     * 벌금 부여 취소
     * */
    @DeleteMapping("teacher/law/fine")
    @ResponseBody
    public ResponseEntity<BaseResponseDto<?>> deleteFineStudent(@RequestBody RequestLawNationStudentDto dto){
        System.out.println("삭제할 벌금 부여 "+ dto);
        lawService.deleteFineStudent(dto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success"));
    }
    /**
     * 벌칙 부여 리스트
     */
    @PostMapping("teacher/law/penalty/list")
    public ResponseEntity<BaseResponseDto<?>> getPenaltyLaws(@RequestBody RequestLawNationStudentDto dto){
        List<ResponseLawNationStudentDto> list = lawService.getPenaltyLaws(dto);

        for(ResponseLawNationStudentDto penaltyDto : list) {
            System.out.println(penaltyDto.toString());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success"));
    }

    /**
     * 벌칙 부여
     * */
    @PostMapping("teacher/law/penalty")
    public ResponseEntity<BaseResponseDto<?>> createPenaltyStudent(@RequestBody RequestLawNationStudentDto dto){

        lawService.createPenaltyStudent(dto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success"));
    }

    /**
     * 벌칙 부여 취소
     * */
    @DeleteMapping("teacher/law/penalty")
    public ResponseEntity<BaseResponseDto<?>> deletePenaltyStudent(@RequestBody RequestLawNationStudentDto dto){
        System.out.println("삭제할 벌칙 부여 번호 "+ dto);
        lawService.deletePenaltyStudent(dto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success"));
    }

    /**
     * 벌칙 수행 확인여부
     * */
    @PostMapping("teacher/law/penalty/check")
    public ResponseEntity<BaseResponseDto<?>> updatePenaltyCompleteState(@RequestBody RequestLawNationStudentDto dto){

        long result = lawService.updatePenaltyCompleteState(dto);
        if(result!=0){
            System.out.println("벌칙 수행 체크 완료");
        }else{
            System.out.println("벌칙 수행 체크 실패");
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success"));
    }

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
