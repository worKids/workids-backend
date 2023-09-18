package com.workids.domain.auction.controller;

import com.workids.domain.auction.dto.request.RequestAuctionDoneDto;
import com.workids.domain.auction.dto.request.RequestAuctionListDto;
import com.workids.domain.auction.dto.request.RequestStudentAuctionDto;
import com.workids.domain.auction.dto.request.RequestStudentAuctionListDto;
import com.workids.domain.auction.dto.response.ResponseStudentAuctionDto;
import com.workids.domain.auction.dto.response.ResponseStudentAuctionListDto;
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

import java.util.List;

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

    /**
     * 최근 경매 조회
     * @param dto
     * @return
     */
    @PostMapping("/auction/detail")
    public ResponseEntity<BaseResponseDto<ResponseStudentAuctionDto>> getAuction(@RequestBody RequestAuctionListDto dto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success", studentAuctionService.getAuction(dto)));
    }

    /**
     * 학생별 경매 참여 내역
     * @param dto
     * @return
     */
    @PostMapping("/auction/list")
    public ResponseEntity<BaseResponseDto<List<ResponseStudentAuctionListDto>>> getAuctionList(@RequestBody RequestStudentAuctionListDto dto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success", studentAuctionService.getAuctionList(dto)));
    }
}
