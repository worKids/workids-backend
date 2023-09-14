package com.workids.domain.auction.controller;

import com.workids.domain.auction.dto.request.RequestAuctionDetailDto;
import com.workids.domain.auction.dto.request.RequestAuctionDoneDto;
import com.workids.domain.auction.dto.request.RequestAuctionDto;
import com.workids.domain.auction.dto.request.RequestAuctionListDto;
import com.workids.domain.auction.dto.response.ResponseAuctionDetailDto;
import com.workids.domain.auction.dto.response.ResponseAuctionListDto;
import com.workids.domain.auction.service.AuctionService;
import com.workids.domain.auction.service.TeacherAuctionService;
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
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class TeacherAuctionController {

    private final TeacherAuctionService teacherAuctionService;

    /**
     * 경매 생성
     * @param dto
     * @return
     */
    @PostMapping("/auction")
    public ResponseEntity<BaseResponseDto<?>> createAuction(@RequestBody RequestAuctionDto dto) {
        teacherAuctionService.createAuction(dto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(201, "success"));
    }

    /**
     * 경매 종료
     * @param dto
     * @return
     */
    @PostMapping("/auction/done")
    public ResponseEntity<BaseResponseDto<?>> auctionDone(@RequestBody RequestAuctionDoneDto dto) {
        teacherAuctionService.auctionDone(dto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success"));
    }

    /**
     * 경매 디테일 조회
     * @param dto
     * @return
     */
    @PostMapping("/auction/detail")
    public ResponseEntity<BaseResponseDto<List<ResponseAuctionDetailDto>>> getDetail(@RequestBody RequestAuctionDetailDto dto) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success", teacherAuctionService.getDetail(dto)));
    }

}
