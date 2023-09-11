package com.workids.service;

import com.workids.domain.Law;
import com.workids.domain.LawNationStudent;
import com.workids.domain.Nation;
import com.workids.domain.NationStudent;
import com.workids.dto.request.RequestLawDto;
import com.workids.dto.request.RequestLawNationStudentDto;
import com.workids.dto.response.ResponseLawDto;
import com.workids.dto.response.ResponseLawNationStudentDto;
import com.workids.repository.LawNationStudentRepository;
import com.workids.repository.LawRepository;
import com.workids.repository.NationRepository;
import com.workids.repository.NationStudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LawServiceImpl{

    @Autowired
    private LawRepository lawRepository;

    @Autowired
    private LawNationStudentRepository lawNationStudentRepository;

    @Autowired
    private NationStudentRepository nationStudentRepository;

    @Autowired
    private NationRepository nationRepository;


    /**
     * 법 내역 조회
     * */
    public List<ResponseLawDto> getAllLaws(Long nation_num){

        List<ResponseLawDto> fineList = lawRepository.findByNation(nation_num);

        return fineList;
    }

    /**
     * 법 등록
     * */
    @Transactional
    public void createLaw(long nation_num, RequestLawDto dto){
        Nation nation = nationRepository.findById(nation_num).orElse(null);

        Law law = Law.of(nation, dto);
        lawRepository.save(law);
    }

    /**
     * 법 수정(벌금 가격만 가능)
     * */
    @Transactional
    public int updateLaw(long law_num, int fine) {
        return lawRepository.updateFineByLawNum(law_num,fine);
    }

    /**
     * 법 삭제
     * */
    @Transactional
    public int updateLawState(long law_num){
        return lawRepository.updateStateByLawNum(law_num);
    }

    /**
     * 벌금부여 리스트
     */
    @Transactional
    public List<ResponseLawNationStudentDto> getFineLaws(Long nation_num){
        List<ResponseLawNationStudentDto> fineList = lawNationStudentRepository.findFineByNation(nation_num);

        return fineList;
    };

    /**
     * 벌금 부여
     * */
    @Transactional
    public void createFineStudent(RequestLawNationStudentDto dto){

        NationStudent nationStudent = nationStudentRepository.findByCitizenNumber(dto.getCitizenNumber());
        Law law = lawRepository.findById(dto.getLawNum()).orElse(null);

        LawNationStudent lawNationStudent = LawNationStudent.of(law, nationStudent,dto);
        lawNationStudentRepository.save(lawNationStudent);
        
        //벌금 빼가는 작동 추가적으로 구현
        
    };

    /**
     * 벌금 부여 취소
     * */
    @Transactional
    public void deleteFineStudent(long law_nation_student_num){
        
        //벌금 다시 되돌리는 작동 추가적으로 구현
        
        lawNationStudentRepository.deleteById(law_nation_student_num);
    };

    /**
     * 벌칙 부여 리스트
     */
    @Transactional
    public List<ResponseLawNationStudentDto> getPenaltyLaws(Long nation_num){
        List<ResponseLawNationStudentDto> penaltyList = lawNationStudentRepository.findPenaltyByNation(nation_num);

        return penaltyList;
    };

    /**
     * 벌칙 부여
     * */
    @Transactional
    public void createPenaltyStudent(RequestLawNationStudentDto dto){
        NationStudent nationStudent = nationStudentRepository.findByCitizenNumber(dto.getCitizenNumber());
        Law law = lawRepository.findById(dto.getLawNum()).orElse(null);

        LawNationStudent lawNationStudent = LawNationStudent.of(law, nationStudent,dto);
        lawNationStudentRepository.save(lawNationStudent);
    };

    /**
     * 벌칙 부여 취소
     * */
    public void deletePenaltyStudent(long law_nation_student_num){
        lawNationStudentRepository.deleteById(law_nation_student_num);
    };

    /**
     * 벌칙 수행 확인여부
     * */
    public int updatePenaltyCompleteState(long law_nation_student_num){
        return lawNationStudentRepository.updateStateByLawNationStudentNum(law_nation_student_num);
    };

    /**
     * 학생에게 부여된 벌금 내역 조회
     * */
    public List<ResponseLawNationStudentDto> getStudentFineLaws(long nation_student_num){
        return lawNationStudentRepository.findStudentFine(nation_student_num);
    }
    /**
     * 학생에게 부여된 벌칙 내역 조회
     * */
    public List<ResponseLawNationStudentDto>  getStudentPenaltyLaws(long nation_student_num){
        return lawNationStudentRepository.findStudentPenalty(nation_student_num);
    }
}
