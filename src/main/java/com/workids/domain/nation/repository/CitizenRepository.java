package com.workids.domain.nation.repository;

import com.workids.domain.nation.entity.Citizen;
import com.workids.domain.nation.entity.Nation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

@Repository
public interface CitizenRepository extends JpaRepository<Citizen, Long>, QuerydslPredicateExecutor<Citizen> {
    List<Citizen> findByNation_NationNum(Long nationNum);

    Citizen findByCitizenNumber(int nationNumber);

    @Query(value =  "UPDATE NATION_STUDENT SET CREDIT_RATING = ?1 WHERE NATION_NUM = ?2 AND CITIZEN_NUMBER = ?3", nativeQuery = true)
    @Modifying
    void update(int creditRating, Long nationNum, int citizenNumber);

    Citizen findByCitizenNum(Long citizenNum);

    void deleteAllInBatch(Iterable<Citizen> citizens);


}
