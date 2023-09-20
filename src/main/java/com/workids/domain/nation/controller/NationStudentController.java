package com.workids.domain.nation.controller;

import com.workids.domain.nation.dto.request.RequestNationStudentDto;
import com.workids.domain.nation.dto.request.RequestNumDto;
import com.workids.domain.nation.dto.request.RequestNationStudentJoinDto;
import com.workids.domain.nation.dto.response.ResponseNationStudentDto;
import com.workids.domain.nation.dto.response.ResponseStudentNationListDto;
import com.workids.domain.nation.service.NationService;
import com.workids.domain.nation.service.NationStudentService;
import com.workids.global.comm.BaseResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/student")
@RequiredArgsConstructor
public class NationStudentController {

    private final NationStudentService nationStudentService;
    private final NationService nationService;


    /**
     * 학생 -> 나라 참여 시 생성
     */
    @PostMapping("/nation/join")
    @ResponseBody
    public ResponseEntity<BaseResponseDto<?>> join(@RequestBody RequestNationStudentJoinDto dto){

        nationStudentService.join(dto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success"));

    }

    /**
     * 학생이 가입한 나라 조회
     * POST: /student/nation/list
     */

    @PostMapping("/nation/list")
    @ResponseBody
    public ResponseEntity<BaseResponseDto<List<ResponseStudentNationListDto>>> getListAll(@RequestBody RequestNumDto dto){
        List<ResponseStudentNationListDto> nationList = nationService.getStudentNationList(dto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success", nationList));

    }

    /**
     * 나라 학생 번호 조회
     * @param dto
     * @return
     */
    @PostMapping("/nation")
    public ResponseEntity<BaseResponseDto<ResponseNationStudentDto>> getNationStudentNum(
            @RequestBody RequestNationStudentDto dto) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success", nationStudentService.getNationStudentNum(dto)));
    }


}
