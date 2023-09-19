package com.workids.domain.ranking.controller;

import com.workids.domain.ranking.dto.request.RequestRankingDto;
import com.workids.domain.ranking.dto.response.ResponseRankingResultDto;
import com.workids.domain.ranking.service.RankingService;
import com.workids.global.comm.BaseResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RankingController {

    @Autowired
    private RankingService rankingService;
    
    /**
     * 랭킹 조회
     * */
    @PostMapping("/ranking/save/list")
    public ResponseEntity<BaseResponseDto<ResponseRankingResultDto>> getRanking(@RequestBody RequestRankingDto dto){
        //dto => nationNum, type 필요

        ResponseRankingResultDto list = rankingService.getRanking(dto);

        System.out.println(list.toString());

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success", list));
    }

    /**
     * 학생 자신의 랭킹 조회
     * */
    public ResponseEntity<BaseResponseDto<?>> getMyRanking(){

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success"));
    }
}
