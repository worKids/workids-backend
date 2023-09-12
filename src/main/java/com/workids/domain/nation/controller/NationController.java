package com.workids.domain.nation.controller;

import com.workids.domain.nation.dto.request.RequestNationInfoDto;
import com.workids.domain.nation.dto.response.ResponseNationInfoDto;
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

@Controller
@RequestMapping("/nation")
@RequiredArgsConstructor
public class NationController {


    private final NationService nationService;


    /**
     * 나라 정보 조회
     * POST: /nation/list
     */
    @PostMapping("/list")
    @ResponseBody
    public ResponseEntity<BaseResponseDto<ResponseNationInfoDto>> getInfo(@RequestBody RequestNationInfoDto dto){
        ResponseNationInfoDto infoDto = nationService.getInfo(dto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success", infoDto));

    }


    /**
     * 나라 정보 수정
     * PATCH: /nation
     */

    /**
     * 나라 삭제
     * DELETE: /nation
     */

    /**
     * 나라 메인페이지(나라정보, 법/직업/소비항목 조회) - teacher, student 동일
     * POST: /teacher/nation
     */



}
