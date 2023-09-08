package com.workids.repository;

import com.workids.domain.Nation;
import com.workids.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NationRepository extends JpaRepository<Nation, Long>{

}
