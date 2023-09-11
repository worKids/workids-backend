package com.workids.domain.nation.controller;

import com.workids.domain.nation.dto.request.NationJoinDto;
import com.workids.domain.nation.dto.request.NationListALLDto;
import com.workids.domain.nation.dto.response.NationListResponseDto;
import com.workids.domain.nation.entity.Nation;
import com.workids.domain.nation.service.NationService;
import com.workids.domain.user.dto.request.StudentJoinDto;
import com.workids.global.comm.BaseResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class NationController {


    private final NationService nationService;

    /**
     * 나라 설립(등록)
     */
    @PostMapping("/nation/join")
    @ResponseBody
    public ResponseEntity<BaseResponseDto<?>> join(@RequestBody NationJoinDto dto){
        nationService.join(dto);

        System.out.println("나라 등록 완료");
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success"));

    }

    /**
     * 참여중인 나라 조회
     * POST: /teacher/nation/list
     */

    @PostMapping("/nation/list")
    @ResponseBody
    public ResponseEntity<BaseResponseDto<List<NationListResponseDto>>> getListAll(@RequestBody NationListALLDto dto){
        List<NationListResponseDto> list = nationService.getListAll(dto.getTeacherNum());

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseDto<>(200, "success", list));

    }





    /**
     * 나라 메인페이지(나라정보, 법/직업/소비항목 조회)
     */

    ////

    /**
     * 나라 정보 조회
     * POST: /nation/list
     */

    /**
     * 나라 정보 수정
     * PATCH: /nation
     */

    /**
     * 나라 삭제
     * DELETE: /nation
     */


}
