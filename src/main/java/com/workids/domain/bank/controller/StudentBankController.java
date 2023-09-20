package com.workids.domain.bank.controller;

import com.workids.domain.bank.dto.request.*;
import com.workids.domain.bank.dto.response.ResponseBankStudentAssetDto;
import com.workids.domain.bank.dto.response.ResponseBankStudentListDto;
import com.workids.domain.bank.dto.response.ResponseBankStudentJoinListDto;
import com.workids.domain.bank.dto.response.ResponseBankTransactionListDto;
import com.workids.domain.bank.service.StudentBankService;
import com.workids.global.comm.BaseResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
        List<ResponseBankStudentListDto> list = studentBankService.getBankList(dto.getNationNum());

        // 결과 확인
        for (ResponseBankStudentListDto bankStudentDto : list){
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
    
    /**
     * 예금 계좌 중도 해지
     * PATCH: /student/bank/deposit/cancel
     */
    @PatchMapping("/deposit/cancel")
    @ResponseBody
    public ResponseEntity<BaseResponseDto<?>> updateBankDepositState(@RequestBody RequestBankStudentUpdateStateDto dto){
        // 예금 계좌 중도 해지
        studentBankService.updateBankDepositState(dto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success"));
    }

    /**
     * 예금 계좌 목록 조회
     * POST: /student/bank/deposit/list
     */
    @PostMapping("/deposit/list")
    @ResponseBody
    public ResponseEntity<BaseResponseDto<?>> getDepositList(@RequestBody RequestBankStudentJoinListDto dto){
        // 예금 계좌 목록 조회
        List<ResponseBankStudentJoinListDto> list = studentBankService.getDepositList(dto.getNationStudentNum());

        // 결과 확인
        for (ResponseBankStudentJoinListDto bankStudentDto : list){
            System.out.println(bankStudentDto);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success", list));
    }

    /**
     * 주거래 계좌 목록 조회
     * POST: /student/bank/main/list
     */
    @PostMapping("/main/list")
    @ResponseBody
    public ResponseEntity<BaseResponseDto<?>> getMainList(@RequestBody RequestBankStudentJoinListDto dto){
        // 주거래 계좌 목록 조회
        List<ResponseBankStudentJoinListDto> list = studentBankService.getMainList(dto.getNationStudentNum());

        // 결과 확인
        for (ResponseBankStudentJoinListDto bankStudentDto : list){
            System.out.println(bankStudentDto);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success", list));
    }

    /**
     * 계좌 상세 거래내역 조회
     * POST: /student/bank/transaction/list
     */
    @PostMapping("/transaction/list")
    @ResponseBody
    public ResponseEntity<BaseResponseDto<?>> getTransactionList(@RequestBody RequestBankTransactionListDto dto){
        // 계좌 상세 거래내역 조회
        List<ResponseBankTransactionListDto> list = studentBankService.getTransactionList(dto.getBankNationStudentNum());

        // 결과 확인
        for (ResponseBankTransactionListDto transactionDto : list){
            System.out.println(transactionDto);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success", list));
    }

    /**
     * 총 자산 조회
     * POST: /student/bank/asset
     */
    @PostMapping("/asset")
    @ResponseBody
    public ResponseEntity<BaseResponseDto> getAsset(@RequestBody RequestBankStudentJoinListDto dto){
        // 총 자산 조회
        ResponseBankStudentAssetDto asset = studentBankService.getAsset(dto.getNationStudentNum());

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success", asset));
    }
}
