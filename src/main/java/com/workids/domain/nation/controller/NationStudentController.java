package com.workids.domain.nation.controller;

import com.workids.domain.nation.dto.request.RequestNationJoinDto;
import com.workids.domain.nation.dto.request.RequestNationStudentJoinDto;
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

@Controller
@RequestMapping("/student")
@RequiredArgsConstructor
public class NationStudentController {

    private final NationStudentService nationStudentService;

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
}
