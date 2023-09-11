package com.workids.service;

import com.workids.dto.request.RequestLawDto;
import com.workids.dto.request.RequestLawNationStudentDto;
import com.workids.dto.response.ResponseLawDto;
import com.workids.dto.response.ResponseLawNationStudentDto;

import java.util.List;

public interface LawService {
    /**
     * 법 내역 조회
     * */
    List<ResponseLawDto> getAllLaws(Long nation_num);

    /**
     *  법 제정(등록)
     * */
    void createLaw(long nation_num, RequestLawDto dto);

    /**
     * 법 수정(벌금 가격만 가능)
     * */
    int updateLaw(long law_num, int fine);

    /**
     * 법 삭제
     * */
    int updateLawState(long law_num);

    /**
     * 벌금부여 리스트
     */
    List<ResponseLawNationStudentDto> getFineLaws(Long nation_num);

    /**
     * 벌금 부여
     * */
    void createFineStudent(RequestLawNationStudentDto dto);

    /**
     * 벌금 부여 취소
     * */
    void deleteFineStudent(long law_nation_student_num);

    /**
     * 벌칙 부여 리스트
     */
    List<ResponseLawNationStudentDto> getPenaltyLaws(Long nation_num);

    /**
     * 벌칙 부여
     * */
    void createPenaltyStudent(RequestLawNationStudentDto dto);

    /**
     * 벌칙 부여 취소
     * */
    void deletePenaltyStudent(long law_nation_student_num);

    /**
     * 벌칙 수행 확인여부
     * */
    int updatePenaltyCompleteState(long law_nation_student_num);

    /**
     * 학생에게 부여된 벌금 내역 조회
     * */
    List<ResponseLawNationStudentDto> getStudentFineLaws(long nation_student_num);

    /**
     * 학생에게 부여된 벌칙 내역 조회
     * */
    List<ResponseLawNationStudentDto> getStudentPenaltyLaws(long nation_student_num);
}
