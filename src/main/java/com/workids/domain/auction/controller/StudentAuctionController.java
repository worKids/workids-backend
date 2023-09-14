package com.workids.domain.auction.controller;

import com.workids.domain.auction.dto.request.RequestStudentAuctionDto;
import com.workids.domain.auction.service.AuctionService;
import com.workids.domain.auction.service.StudentAuctionService;
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
public class StudentAuctionController {

    private final StudentAuctionService studentAuctionService;

    /**
     * 학생 부동산 입찰
     * @param dto
     * @return
     */
    @PostMapping("/auction")
    public ResponseEntity<BaseResponseDto<?>> bidSeat(@RequestBody RequestStudentAuctionDto dto) {
        studentAuctionService.bidSeat(dto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(201, "success"));
    }

}
