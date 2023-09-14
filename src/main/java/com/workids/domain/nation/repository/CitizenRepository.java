package com.workids.domain.nation.repository;

import com.workids.domain.nation.entity.Citizen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CitizenRepository extends JpaRepository<Citizen, Long> {



    List<Citizen> findByNation_NationNum(Long nationNum);


    @Query(value =  "UPDATE NATION_STUDENT SET CREDIT_RATING = ?1 WHERE NATION_NUM = ?2 AND CITIZEN_NUMBER = ?3", nativeQuery = true)
    @Modifying
    void update(int creditRating, Long nationNum, int citizenNumber);


}
