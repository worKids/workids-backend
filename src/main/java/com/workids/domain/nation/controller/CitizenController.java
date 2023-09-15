package com.workids.domain.nation.controller;

import com.workids.domain.nation.dto.request.RequestCitizenJoinDto;
import com.workids.domain.nation.dto.request.RequestCitizenUpdateDto;
import com.workids.domain.nation.dto.request.RequestNationUpdateDto;
import com.workids.domain.nation.dto.request.RequestNumDto;
import com.workids.domain.nation.service.CitizenService;
import com.workids.global.comm.BaseResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    @PatchMapping("/citizen")
    @ResponseBody
    public ResponseEntity<BaseResponseDto<?>> update(@RequestBody RequestCitizenUpdateDto dto){

        citizenService.update(dto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success"));

    }

    /**
     * 국민목록 삭제
     */
    @DeleteMapping("/citizen")
    @ResponseBody
    public ResponseEntity<BaseResponseDto<?>> delete(@RequestBody RequestNumDto dto){

        citizenService.delete(dto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success"));

    }
}
