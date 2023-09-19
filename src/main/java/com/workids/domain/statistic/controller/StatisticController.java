package com.workids.domain.statistic.controller;

import com.workids.domain.statistic.dto.request.RequestStatisticDto;
import com.workids.domain.statistic.dto.response.ResponseAssetDto;
import com.workids.domain.statistic.dto.response.ResponseExpendDto;
import com.workids.domain.statistic.dto.response.ResponseStatisticDto;
import com.workids.domain.statistic.service.StatisticService;
import com.workids.global.comm.BaseResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/student")
@RequiredArgsConstructor
public class StatisticController {
    private final StatisticService statisticService;

    @PostMapping("/statistic")
    public ResponseEntity<BaseResponseDto<ResponseStatisticDto>> getStatistic(@RequestBody RequestStatisticDto dto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success", statisticService.getStatistic(dto)));

    }
}
