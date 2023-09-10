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
    @RequestMapping("law/list")
    public ResponseEntity<BaseResponseDto<?>> getAllLaws(long nation_num){

        List<ResponseLawDto> list = lawService.getAllLaws(nation_num);

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
    public ResponseEntity<BaseResponseDto<?>> createLaw(@RequestParam long nation_num, @RequestBody RequestLawDto dto){

        System.out.println("삽입할 법 "+ dto);
        lawService.createLaw(nation_num, dto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success"));
    }

    /**
     * 법 수정(벌금 가격만 가능)
     * */
    @PatchMapping("teacher/law/update")
    @ResponseBody
    public ResponseEntity<BaseResponseDto<?>> updateLaw(@RequestParam long law_num,@RequestParam int fine){

        System.out.println("수정할 법 "+ law_num);
        int result = lawService.updateLaw(law_num, fine);
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
    @PatchMapping("teacher/law/delete")
    @ResponseBody
    public ResponseEntity<BaseResponseDto<?>> updateLawState(@RequestParam long law_num){

        System.out.println("삭제할 법 "+ law_num);
        int result = lawService.updateLawState(law_num);
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
    public ResponseEntity<BaseResponseDto<?>> getFineLaws(@RequestParam long nation_num){
        List<ResponseLawNationStudentDto> list = lawService.getFineLaws(nation_num);

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
    public ResponseEntity<BaseResponseDto<?>> deleteFineStudent(@RequestParam long law_nation_student_num){
        System.out.println("삭제할 벌금 부여 번호 "+ law_nation_student_num);
        lawService.deleteFineStudent(law_nation_student_num);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success"));
    }
    /**
     * 벌칙 부여 리스트
     */
    @PostMapping("teacher/law/penalty/list")
    public ResponseEntity<BaseResponseDto<?>> getPenaltyLaws(@RequestParam long nation_num){
        List<ResponseLawNationStudentDto> list = lawService.getPenaltyLaws(nation_num);

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
    public ResponseEntity<BaseResponseDto<?>> deletePenaltyStudent(@RequestParam long law_nation_student_num){
        System.out.println("삭제할 벌칙 부여 번호 "+ law_nation_student_num);
        lawService.deletePenaltyStudent(law_nation_student_num);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success"));
    }

    /**
     * 벌칙 수행 확인여부
     * */
    @PostMapping("teacher/law/penalty/check")
    public ResponseEntity<BaseResponseDto<?>> updatePenaltyCompleteState(@RequestParam long law_nation_student_num){

        int result = lawService.updatePenaltyCompleteState(law_nation_student_num);
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
    public ResponseEntity<BaseResponseDto<?>> getStudentFineLaws(@RequestParam long nation_student_num){

        List<ResponseLawNationStudentDto> list = lawService.getStudentFineLaws(nation_student_num);

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
    public ResponseEntity<BaseResponseDto<?>> getStudentPenaltyLaws(@RequestParam long nation_student_num){

        List<ResponseLawNationStudentDto> list = lawService.getStudentPenaltyLaws(nation_student_num);

        for(ResponseLawNationStudentDto penaltyDto : list) {
            System.out.println(penaltyDto.toString());
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success"));
    }
}
