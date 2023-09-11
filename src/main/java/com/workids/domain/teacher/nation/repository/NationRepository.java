package com.workids.domain.teacher.nation.repository;

import com.workids.domain.teacher.nation.entity.Nation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NationRepository extends JpaRepository<Nation, Long>{

}
