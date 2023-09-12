package com.workids.domain.nation.controller;

import com.workids.domain.nation.dto.request.RequestCitizenJoinDto;
import com.workids.domain.nation.dto.request.RequestNationJoinDto;
import com.workids.domain.nation.service.CitizenService;
import com.workids.domain.nation.service.NationService;
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
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class CitizenController {
    private final CitizenService citizenService;

    /**
     * 국민 목록 등록
     * POST: /teacher/citizen
     */
    @PostMapping("/citizen")
    @ResponseBody
    public ResponseEntity<BaseResponseDto<?>> join(@RequestBody List<RequestCitizenJoinDto> dtoList){
        citizenService.join(dtoList);

        System.out.println("국민목록 등록 완료");
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success"));

    }

    /**
     * 국민 목록 수정
     * PATCH: /teacher/citizen
     */
}
