package com.workids.domain.auction.controller;

import com.workids.domain.auction.dto.request.RequestAuctionListDto;
import com.workids.domain.auction.dto.response.ResponseAuctionListDto;
import com.workids.domain.auction.service.AuctionService;
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
@RequestMapping("/auction")
@RequiredArgsConstructor
public class AuctionController {

    private final AuctionService auctionService;

    /**
     * 나라별 경매 조회
     * @param dto
     * @return
     */
    @PostMapping("/list")
    public ResponseEntity<BaseResponseDto<List<ResponseAuctionListDto>>> getAuctionList(
            @RequestBody RequestAuctionListDto dto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success", auctionService.getAuctionList(dto)));
    }
}
