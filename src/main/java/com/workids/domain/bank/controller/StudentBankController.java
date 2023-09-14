package com.workids.domain.bank.controller;

import com.workids.domain.bank.dto.request.RequestBankStudentCreateDto;
import com.workids.domain.bank.dto.request.RequestBankListDto;
import com.workids.domain.bank.dto.response.ResponseStudentBankDto;
import com.workids.domain.bank.service.StudentBankService;
import com.workids.global.comm.BaseResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Student 은행 Controller
 */
@Controller
@RequestMapping("/student/bank")
@RequiredArgsConstructor
public class StudentBankController {
    private final StudentBankService studentBankService;

    /**
     * 전체 은행 상품 조회(현재 사용중 모두 조회, 주거래 통장 상품 제외)
     * POST: /student/bank/list
     */
    @PostMapping("/list")
    @ResponseBody
    public ResponseEntity<BaseResponseDto<?>> getBankList(@RequestBody RequestBankListDto dto){
        // 전체 은행 상품 조회
        List<ResponseStudentBankDto> list = studentBankService.getBankList(dto.getNationNum());

        // 결과 확인
        for (ResponseStudentBankDto bankStudentDto : list){
            System.out.println(bankStudentDto);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success", list));
    }

    /**
     * 은행 상품 가입(예금)
     * POST: /student/bank/deposit
     */
    @PostMapping("/deposit")
    @ResponseBody
    public ResponseEntity<BaseResponseDto<?>> createBankDeposit(@RequestBody RequestBankStudentCreateDto dto){
        // 은행 상품 가입
        studentBankService.createBankDeposit(dto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success"));
    }
}
