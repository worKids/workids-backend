package com.workids.domain.ranking.controller;

import com.workids.domain.ranking.dto.request.RequestRankingDto;
import com.workids.domain.ranking.dto.response.ResponseRankingResultDto;
import com.workids.domain.ranking.service.RankingService;
import com.workids.global.comm.BaseResponseDto;
import com.workids.global.exception.ApiException;
import com.workids.global.exception.ExceptionEnum;
import com.workids.global.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequiredArgsConstructor
public class RankingController {

    @Autowired
    private RankingService rankingService;

    private final JwtTokenProvider jwtTokenProvider;
    
    /**
     * 랭킹 조회
     * */
    @PostMapping("/ranking/list")
    @ResponseBody
    public ResponseEntity<BaseResponseDto<ResponseRankingResultDto>> getRanking(HttpServletRequest request, @RequestBody RequestRankingDto dto){
        //dto => nationNum, type 필요

        if (!jwtTokenProvider.validateToken(request.getHeader("Authorization"))) {
            throw new ApiException(ExceptionEnum.MEMBER_ACCESS_EXCEPTION);
        };

        ResponseRankingResultDto resultDto = rankingService.getRanking(dto);

        System.out.println(resultDto.toString());

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success", resultDto));
    }

}
