package com.workids.domain.consumption.repository;

import com.workids.domain.consumption.entity.Consumption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ConsumptionRepository extends JpaRepository<Consumption, Long>, QuerydslPredicateExecutor<Consumption> {

    /**
     * 나라 소비 항목 전체 조회
     * */
    /*@Query("select new com.workids.dto.response.ResponseConsumptionDto (c.consumptionNum,c.content, c.amount,c.updateDate) from Consumption c where c.nation.nationNum = ?1 and c.state=0")
    List<ResponseConsumptionDto> findByNation(Long nation_num);*/

}
