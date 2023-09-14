package com.workids.domain.citizen.controller;

import com.workids.domain.citizen.dto.request.RequestCitizenDto;
import com.workids.domain.citizen.service.TeacherCitizenService;
import com.workids.global.comm.BaseResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
     */
  /* @PostMapping("/teacher/citizen/list")
    @ResponseBody
    public ResponseEntity<BaseResponseDto<?>> citizenList(Model model, @RequestBody RequestCitizenDto citizenDto) {
        List<ResponseCitizenDto> citizen = citizenService.citizenList(citizenDto);
       model.addAttribute("citizen", citizen);
       System.out.println("citizen = " + citizen);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success"));
    }*/



}
