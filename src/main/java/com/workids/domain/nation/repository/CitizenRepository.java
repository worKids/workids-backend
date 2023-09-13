package com.workids.domain.nation.repository;

import com.workids.domain.nation.entity.Citizen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CitizenRepository extends JpaRepository<Citizen, Long> {

    List<Citizen> findByNation_NationNum(Long nationNum);


}
