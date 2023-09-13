package com.workids.domain.nation.controller;

import com.workids.domain.nation.dto.request.RequestNationJoinDto;
import com.workids.domain.nation.dto.request.RequestNationListAllDto;
import com.workids.domain.nation.dto.response.ResponseNationListAllDto;
import com.workids.domain.nation.entity.Nation;
import com.workids.domain.nation.service.NationService;
import com.workids.global.comm.BaseResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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
     * 참여중인 나라 조회
     * POST: /teacher/nation/list
     */

    @PostMapping("/nation/list")
    @ResponseBody
    public ResponseEntity<BaseResponseDto<List<ResponseNationListAllDto>>> getListAll(@RequestBody RequestNationListAllDto dto){
         List<ResponseNationListAllDto> nationList = nationService.getListAll(dto);

        //List<ResponseNationListAllDto> list;
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success", nationList));

    }



}
