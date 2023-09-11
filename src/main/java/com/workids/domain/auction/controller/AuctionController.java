package com.workids.domain.auction.controller;

import com.workids.domain.auction.dto.request.RequestAuctionDto;
import com.workids.domain.auction.service.AuctionService;
import com.workids.domain.user.service.StudentService;
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
@RequestMapping()
@RequiredArgsConstructor
public class AuctionController {

    private final AuctionService auctionService;

    /**
     * 경매 생성(
     * @param dto
     * @return
     */
    @PostMapping("/teacher/auction")
    public ResponseEntity<BaseResponseDto<?>> createAuction(@RequestBody RequestAuctionDto dto) {
        auctionService.createAuction(dto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success"));
    }
//
//    @PostMapping("/auction/list")
//    public ResponseEntity<BaseResponseDto<List<>>>
}
