package com.workids.controller;

import com.workids.dto.BaseResponseDto;
import com.workids.dto.TokenDto;
import com.workids.dto.request.JoinDto;
import com.workids.dto.request.LoginDto;
import com.workids.service.StudentService;
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
