package com.workids.domain.job.controller;

import com.workids.domain.job.dto.request.RequestJobDto;
import com.workids.domain.job.dto.request.RequestStudentJobDto;
import com.workids.domain.job.dto.response.ResponseJobDto;
import com.workids.domain.job.dto.response.ResponseMyJobDto;
import com.workids.domain.job.dto.response.ResponseStudentJobDto;
import com.workids.domain.job.service.StudentJobService;
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
public class StudentJobController {

    private final StudentJobService jobService;

    /**
     * 나라의 직업전체조회
     */
    @PostMapping("/student/job/list")
    @ResponseBody
    public ResponseEntity<BaseResponseDto<?>> selectByNation(Model model, @RequestBody RequestJobDto jobDto) {
        List<ResponseJobDto> job = jobService.selectByNation(jobDto);
        System.out.println(job);
        model.addAttribute("job", job);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success", job));
    }

    /**
     * 내 직업 조회
     */
    @PostMapping("/student/job/my/list")
    @ResponseBody
    public ResponseEntity<BaseResponseDto<?>> selectMyJob(Model model, @RequestBody RequestStudentJobDto studentjobDto) {
        List<ResponseMyJobDto> myjob = jobService.selectMyJob(studentjobDto);
        model.addAttribute("myjob", myjob);
        System.out.println(myjob);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success", myjob));
    }
}
