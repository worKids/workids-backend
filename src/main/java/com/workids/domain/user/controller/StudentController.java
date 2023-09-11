package com.workids.domain.user.controller;

import com.workids.domain.user.dto.request.JoinDto;
import com.workids.domain.user.dto.request.LoginDto;
import com.workids.domain.user.service.StudentService;
import com.workids.global.comm.BaseResponseDto;
import com.workids.global.comm.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    /**
     * 회원가입
     */
    @PostMapping("/account/join")
    @ResponseBody
    public ResponseEntity<BaseResponseDto<?>> join(@RequestBody JoinDto dto){
        studentService.join(dto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success"));

    }

    /**
     * 로그인
     */
    @PostMapping("/account/login")
    @ResponseBody
    public ResponseEntity<TokenDto> login(@RequestBody LoginDto dto){
        String token = studentService.login(dto);


        // 토큰을 Response Header, Body 모두에 넣어준다.
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + token);

        return new ResponseEntity<>(new TokenDto(token), httpHeaders, HttpStatus.OK);

    }

}
