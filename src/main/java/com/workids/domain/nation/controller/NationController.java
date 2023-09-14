package com.workids.domain.nation.controller;

import com.workids.domain.nation.dto.request.RequestNationInfoDto;
import com.workids.domain.nation.dto.request.RequestNationUpdateDto;
import com.workids.domain.nation.dto.response.ResponseNationInfoDto;
import com.workids.domain.nation.service.NationService;
import com.workids.global.comm.BaseResponseDto;
import com.workids.global.exception.ApiException;
import com.workids.global.exception.ExceptionEnum;
import com.workids.global.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/nation")
@RequiredArgsConstructor
public class NationController {


    private final NationService nationService;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 나라 정보 조회
     * POST: /nation/list
     */
    @PostMapping("/list")
    @ResponseBody
    public ResponseEntity<BaseResponseDto<ResponseNationInfoDto>> getInfo(HttpServletRequest request, @RequestBody RequestNationInfoDto dto){

        if (!jwtTokenProvider.validateToken(request.getHeader("Authorization"))) {
            throw new ApiException(ExceptionEnum.MEMBER_ACCESS_EXCEPTION);
        };
        ResponseNationInfoDto infoDto = nationService.getInfo(dto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success", infoDto));

    }


    /**
     * 나라 정보 수정
     * PATCH: /nation
     */
    @PatchMapping("/update")
    @ResponseBody
    public ResponseEntity<BaseResponseDto<?>> updateNation(@RequestBody RequestNationUpdateDto dto){

        System.out.println("들어오나?");

        nationService.update(dto);

        System.out.println("nation 업데이트 성공");
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success"));

    }


    /**
     * 나라 삭제
     * DELETE: /nation
     */

    /**
     * 나라 메인페이지(나라정보, 법/직업/소비항목 조회) - teacher, student 동일
     * POST: /teacher/nation
     */



}
