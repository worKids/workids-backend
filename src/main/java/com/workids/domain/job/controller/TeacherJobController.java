package com.workids.domain.job.controller;

import com.workids.domain.job.dto.request.RequestJobDto;
import com.workids.domain.job.dto.request.RequestStudentJobDto;
import com.workids.domain.job.dto.response.ResponseJobDto;
import com.workids.domain.job.dto.response.ResponseStudentJobDto;
import com.workids.domain.job.service.TeacherJobService;
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
public class TeacherJobController {

    private final TeacherJobService jobService;

    /**
     * 나라의 직업전체조회
     */
   @PostMapping("/job/list")
    @ResponseBody
    public ResponseEntity<BaseResponseDto<?>> selectByNation(Model model, @RequestBody RequestJobDto jobDto) {
        List<ResponseJobDto> job = jobService.selectByNation(jobDto);
       model.addAttribute("job", job);
       System.out.println("job = " + job);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success"));
    }

    /**
     * 직업 생성
     */
    @PostMapping("/teacher/job")
    @ResponseBody
    public ResponseEntity<BaseResponseDto<?>> createJob(@RequestBody RequestJobDto jobdto) {
        jobService.createJob(jobdto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success"));
    }

    /**
     * 직업 삭제
     */
    @PatchMapping("/teacher/job/citizen/hide")
    @ResponseBody
    public ResponseEntity<BaseResponseDto<?>> deleteByJobNum(@RequestBody RequestJobDto jobDto) {

        jobService.delete(jobDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success"));
    }

    /**
     * 직업부여리스트
     */
    @PostMapping("/teacher/job/citizen/list")
    @ResponseBody
    public ResponseEntity<BaseResponseDto<?>> citizenJobList(Model model,@RequestBody RequestStudentJobDto studentJobDto) {

        List<ResponseStudentJobDto> jobList = jobService.studentJobList(studentJobDto);
        System.out.println("jobList = " +jobList);
        model.addAttribute("jobList", jobList);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success"));
    }



    /**
     * 직업부여수정
     */
    @PatchMapping("/teacher/job/citizen")
    @ResponseBody
    public ResponseEntity<BaseResponseDto<?>> studentJobUpdate(@RequestBody RequestStudentJobDto studentjobDto) {

        jobService.studentJobUpdate(studentjobDto);
        System.out.println("success");
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success"));
    }


}
