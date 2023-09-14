package com.workids.domain.citizen.controller;

import com.workids.domain.citizen.dto.request.RequestCitizenDto;
import com.workids.domain.citizen.dto.response.ResponseCitizenCreditDto;
import com.workids.domain.citizen.dto.response.ResponseCitizenDto;
import com.workids.domain.citizen.service.TeacherCitizenService;
import com.workids.global.comm.BaseResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TeacherCitizenController {

    private final TeacherCitizenService citizenService;

    /**
     * 국민전체조회
     * {
     *     "nationNum" : 1;
     * }
     */
    @PostMapping("/teacher/citizen/list")
    @ResponseBody
    public ResponseEntity<BaseResponseDto<?>> citizenList(Model model, @RequestBody RequestCitizenDto citizenDto) {
        List<ResponseCitizenDto> citizenList = citizenService.citizenList(citizenDto);
       model.addAttribute("citizen", citizenList);
       System.out.println("citizen = " + citizenList);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success", citizenList));
    }

    /**
     * 자산 조회
     */
   /* @PostMapping("/teacher/citizen/asset/list")
    @ResponseBody
    public ResponseEntity<BaseResponseDto<?>> asset(Model model, @RequestBody RequestCitizenDto citizenDto) {
        List<ResponseCitizenInfoDto> info = citizenService.citizenList(citizenDto);
        model.addAttribute("citizen", info);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success", info));
    }*/

    /**
     * 신용도 전체조회
     * {
     *     "nationNum" : 1
     * }
     */
    @PostMapping("/teacher/citizen/credit/list")
    @ResponseBody
    public ResponseEntity<BaseResponseDto<?>> creditList(Model model, @RequestBody RequestCitizenDto citizenDto) {
        List<ResponseCitizenCreditDto> creditList = citizenService.creditList(citizenDto);
        model.addAttribute("creditList", creditList);
        System.out.println("creditList = " + creditList);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success", creditList));
    }

    /**
     * 신용도 수정
     * {
     *     "nationNum" : 1,
     *     "citizenNum" : 1,
     *     "creditRating" : 1
     * }
     */
    @PatchMapping("/teacher/citizen/credit")
    @ResponseBody
    public ResponseEntity<BaseResponseDto<?>> updateCredit(Model model, @RequestBody RequestCitizenDto citizenDto) {
       citizenService.updateCredit(citizenDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success"));
    }




}
