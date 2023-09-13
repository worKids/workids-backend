package com.workids.domain.nation.controller;

import com.workids.domain.nation.dto.request.RequestNationJoinDto;
import com.workids.domain.nation.dto.request.RequestNationListAllDto;
import com.workids.domain.nation.dto.response.ResponseNationListAllDto;
import com.workids.domain.nation.entity.Nation;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class TeacherNationController {

    private final JwtTokenProvider jwtTokenProvider;
    private final NationService nationService;

    /**
     * 나라 설립(등록)
     */
    @PostMapping("/nation/join")
    @ResponseBody
    public ResponseEntity<BaseResponseDto<?>> join(@RequestBody RequestNationJoinDto dto){
        nationService.join(dto);

        System.out.println("나라 등록 완료");
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success"));

    }

    /**
     * 참여중인 나라 조회
     * POST: /teacher/nation/list
     */

    @PostMapping("/nation/list")
    @ResponseBody
    public ResponseEntity<BaseResponseDto<List<ResponseNationListAllDto>>> getListAll(HttpServletRequest request,
                                                                                      @RequestBody RequestNationListAllDto dto){
        if (!jwtTokenProvider.validateToken(request.getHeader("Authorization"))) {
            throw new ApiException(ExceptionEnum.MEMBER_ACCESS_EXCEPTION);
        };
        List<ResponseNationListAllDto> nationList = nationService.getListAll(dto);

        //List<ResponseNationListAllDto> list;
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success", nationList));

    }



}
