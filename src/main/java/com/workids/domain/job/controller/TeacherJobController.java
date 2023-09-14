package com.workids.domain.job.controller;

import com.workids.domain.job.dto.request.RequestJobDto;
import com.workids.domain.job.dto.request.RequestStudentJobDto;
import com.workids.domain.job.dto.response.ResponseJobDto;
import com.workids.domain.job.dto.response.ResponseJobKindDto;
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
     * {
     *     "nationNum" : 1
     * }
     */
   @PostMapping("/job/list")
    @ResponseBody
    public ResponseEntity<BaseResponseDto<?>> selectByNation(Model model, @RequestBody RequestJobDto jobDto) {
        List<ResponseJobDto> job = jobService.selectByNation(jobDto);
       model.addAttribute("job", job);
       System.out.println("job = " + job);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success", job));
    }

    /**
     * 직업 생성
     * {
     *     "nationNum" : 1,
     *     "name" : "한전직원",
     *     "salary" : 100,
     *     "state" : 0,
     *     "content" : "불끄기"
     * },
     * {
     *      "nationNum" : 1,
     *      "name" : "DJ",
     *      "salary" : 100,
     *      "state" : 0,
     *      "content" : "노래틀기"
     * }
     *
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
     * {
     *     "jobNum" : 1
     * }
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
     * {
     *     "nationNum" : 1
     * }
     */
    @PostMapping("/teacher/job/citizen/list")
    @ResponseBody
    public ResponseEntity<BaseResponseDto<?>> citizenJobList(Model model,@RequestBody RequestStudentJobDto studentJobDto) {

        List<ResponseStudentJobDto> jobList = jobService.studentJobList(studentJobDto);
        List<ResponseJobKindDto> jobKind = jobService.jobKindList(studentJobDto);
        System.out.println("jobList = " +jobList);
        System.out.println("jobKind = " + jobKind);
        model.addAttribute("jobKind" , jobKind);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success", jobList));
    }
    /**
     * 직업 종류
     *
     * {
     *     "nationNum" : 1
     * }
     */

    @PostMapping("/teacher/job/kind/list")
    @ResponseBody
    public ResponseEntity<BaseResponseDto<?>> JobKindList(Model model,@RequestBody RequestStudentJobDto studentJobDto) {

        List<ResponseJobKindDto> jobKindList = jobService.jobKindList(studentJobDto);
        model.addAttribute("jobKindList" , jobKindList);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success", jobKindList));
    }

    /**
     * 직업부여
     * {
     *     "citizenNumber" : 1,
     *     "jobNum" : 2,
     *     "nationNum" : 1,
     *     "state" : 0
     * }
     *
     */
    @PostMapping("/teacher/job/citizen/join")
    @ResponseBody
    public ResponseEntity<BaseResponseDto<?>> studentJobJoin(@RequestBody RequestStudentJobDto studentjobDto) {

        jobService.studentJobJoin(studentjobDto);
        System.out.println("success");
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success"));
    }


    /**
     * 직업부여수정
     * {
     *     "citizenNumber" : 1,
     *     "jobNum" : 1,
     *     "nationNum" : 1,
     *     "state" : 0
     * }
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
