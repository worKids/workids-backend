package com.workids.domain.bank.controller;

import com.workids.domain.bank.dto.request.RequestBankTeacherCreateDto;
import com.workids.domain.bank.dto.request.RequestBankListDto;
import com.workids.domain.bank.dto.request.RequestBankUpdateStateDto;
import com.workids.domain.bank.dto.response.ResponseTeacherBankDto;
import com.workids.domain.bank.service.TeacherBankService;
import com.workids.global.comm.BaseResponseDto;
import com.workids.global.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Teacher 은행 Controller
 */
@Controller
@RequestMapping("/teacher/bank")
@RequiredArgsConstructor
public class TeacherBankController {
    private final TeacherBankService teacherBankService;

    /**
     * 은행 상품 등록
     * POST: /teacher/bank
     */
    @PostMapping("")
    @ResponseBody
    public ResponseEntity<BaseResponseDto<?>> createBank(@RequestBody RequestBankTeacherCreateDto dto) {
        // 은행 상품 등록
        teacherBankService.createBank(dto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success"));
    }

    /**
     * 전체 은행 상품 조회(현재 사용중, 미사용중 모두 조회)
     * POST: /teacher/bank/list
     */
    @PostMapping("/list")
    @ResponseBody
    public ResponseEntity<BaseResponseDto<?>> getBankList(@RequestBody RequestBankListDto dto){
        // 전체 은행 상품 조회
        List<ResponseTeacherBankDto> list = teacherBankService.getBankList(dto.getNationNum());

        // 결과 확인
        for (ResponseTeacherBankDto bankTeacherDto : list){
            System.out.println(bankTeacherDto);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success", list));
    }

    /**
     * 은행 상품 삭제
     * PATCH: /teacher/bank/hide
     */
    @PatchMapping("/hide")
    @ResponseBody
    public ResponseEntity<BaseResponseDto<?>> updateBankState(@RequestBody RequestBankUpdateStateDto dto){
        try {
            // 은행 상품 삭제
            teacherBankService.updateBankState(dto.getProductNum());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new BaseResponseDto<>(200, "success"));
        } catch (ApiException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponseDto<>(404, "fail"));
        }
    }
}
