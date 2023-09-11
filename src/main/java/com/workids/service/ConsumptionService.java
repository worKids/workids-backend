package com.workids.service;

import com.workids.dto.response.ResponseConsumptionDto;
import com.workids.repository.ConsumptionNationStudentRepository;
import com.workids.repository.ConsumptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ConsumptionServiceImpl{

    @Autowired
    private ConsumptionRepository consumptionRepository;

    @Autowired
    private ConsumptionNationStudentRepository consumptionNationStudentRepository;

    /**
     * 소비 항목 조회
     * */
    public List<ResponseConsumptionDto> getAllConsumptions(long nation_num){
        return consumptionRepository.findByNation(nation_num);
    }

    /**
     * 소비 항목 추가
     * */

    /**
     * 소비 항목 수정
     * */

    /**
     * 소비 항목 삭제
     * */

    /**
     * 소비 신청 항목(미결재)조회
     * */

    /**
     * 소비 신청 항목(결재)조회
     * */

    /**
     * 소비 신청 미결재 처리
     * */

    /**
     * 소비 신청
     * */

    /**
     * 내 소비 신청 내역 조회(대기, 취소, 승인 거절)
     * */

    /**
     * 신청한 내역 취소하기 (대기 중인 것만 가능)
     * */

    /**
     * 소비 승인 완료 내역 조회(승인)
     * */

}
