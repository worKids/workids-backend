package com.workids.domain.nation.controller;

import com.workids.domain.job.entity.Job;
import com.workids.domain.law.entity.Law;
import com.workids.domain.nation.dto.request.RequestNationJoinDto;
import com.workids.domain.nation.dto.request.RequestNumDto;
import com.workids.domain.nation.dto.response.ResponseNationJobDto;
import com.workids.domain.nation.dto.response.ResponseNationLawDto;
import com.workids.domain.nation.dto.response.ResponseTeacherNationListDto;
import com.workids.domain.nation.service.NationService;
import com.workids.global.comm.BaseResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.workids.domain.nation.dto.response.ResponseTeacherMainDto;


import java.util.List;

@Controller
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class TeacherNationController {


    private final NationService nationService;

    /**
     * 나라 설립(등록)
     */
    @PostMapping("/nation/join")
    @ResponseBody
    public ResponseEntity<BaseResponseDto<?>> join(@RequestBody RequestNationJoinDto dto){
        // 참여코드 발급
        String code = nationService.randomCode();

        // 나라 등록
        nationService.join(dto, code);

        System.out.println("나라 등록 완료");
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success", code));

    }


    /**
     * 선생님이 개설한 나라 조회
     * POST: /teacher/nation/list
     */

    @PostMapping("/nation/list")
    @ResponseBody
    public ResponseEntity<BaseResponseDto<List<ResponseTeacherNationListDto>>> getListAll(@RequestBody RequestNumDto dto){
         List<ResponseTeacherNationListDto> nationList = nationService.getTeacherList(dto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success", nationList));

    }

    /**
     * 나라 삭제
     * DELETE: /teacher/nation
     */
    @DeleteMapping("/nation")
    @ResponseBody
    public ResponseEntity<BaseResponseDto<?>> delete(@RequestBody RequestNumDto dto){

        nationService.delete(dto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success"));

    }
    /**
     * 나라 메인페이지(나라정보, 법/직업/소비항목 조회) - teacher, student 동일
     * POST: /teacher/nation
     */
    @PostMapping("/nation")
    @ResponseBody
    public ResponseEntity<BaseResponseDto<ResponseTeacherMainDto>> getMainInfo(@RequestBody RequestNumDto dto){


        // 나라 정보
        ResponseTeacherMainDto responseTeacherMainDto = nationService.getMainInfo(dto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success", responseTeacherMainDto));

    }

    /**
     * 나라 메인페이지 - 법 조회
     * POST: /teacher/nation/law
     */
    @PostMapping("/nation/law")
    @ResponseBody
    public ResponseEntity<BaseResponseDto<List<ResponseNationLawDto>>> getMainInfoLaw(@RequestBody RequestNumDto dto){

        List<ResponseNationLawDto> lawList = nationService.getMainInfoLaw(dto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success", lawList));

    }

    /**
     * 나라 메인페이지 - 직업 조회
     * POST: /teacher/nation/job
     */
    @PostMapping("/nation/job")
    @ResponseBody
    public ResponseEntity<BaseResponseDto<List<ResponseNationJobDto>>> getMainInfoJob(@RequestBody RequestNumDto dto){

        List<ResponseNationJobDto> jobList = nationService.getMainInfoJob(dto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success", jobList));

    }




}
