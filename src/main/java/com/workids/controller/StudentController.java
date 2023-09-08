package com.workids.controller;


import com.workids.dto.BaseResponseDto;
import com.workids.dto.request.JoinDto;
import com.workids.service.StudentService;
import lombok.RequiredArgsConstructor;
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

    @RequestMapping("/test")
    public void test(){
        System.out.println("hello");
    }
    @PostMapping("/account/join")
    @ResponseBody
    public ResponseEntity<BaseResponseDto<?>> join(@RequestBody JoinDto dto) {
        System.out.println("Controller 요청 받음");
        System.out.println("dto = " + dto);
        studentService.join(dto);
        System.out.println("Controller 요청 보냄");
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success"));

    }
}
