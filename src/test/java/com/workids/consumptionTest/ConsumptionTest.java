package com.workids.consumptionTest;

import com.workids.domain.consumption.entity.Consumption;
import com.workids.domain.consumption.entity.ConsumptionNationStudent;
import com.workids.domain.consumption.repository.ConsumptionNationStudentRepository;
import com.workids.domain.consumption.repository.ConsumptionRepository;
import com.workids.domain.nation.entity.Nation;
import com.workids.domain.nation.entity.NationStudent;
import com.workids.domain.nation.repository.NationRepository;
import com.workids.domain.nation.repository.NationStudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;

@SpringBootTest
@Commit
@Transactional
public class ConsumptionTest {

    @Autowired
    ConsumptionRepository consumptionRepository;

    @Autowired
    ConsumptionNationStudentRepository consumptionNationStudentRepository;

    @Autowired
    NationRepository nationRepository;

    @Autowired
    NationStudentRepository nationStudentRepository;

    @Test
    void consumptionInsert(){

        Nation nationTest = nationRepository.findById(1L).orElse(null);

        consumptionRepository.save(
                Consumption.builder()
                        .nation(nationTest)
                        .content("노래 1곡 선곡")
                        .amount(60)
                        .state(0)
                        .build()
        );
        consumptionRepository.save(
                Consumption.builder()
                        .nation(nationTest)
                        .content("일기쓰기 1회 제외")
                        .amount(70)
                        .state(0)
                        .build()
        );
        consumptionRepository.save(
                Consumption.builder()
                        .nation(nationTest)
                        .content("초콜릿 1개 교환")
                        .amount(30)
                        .state(0)
                        .build()
        );
        consumptionRepository.save(
                Consumption.builder()
                        .nation(nationTest)
                        .content("급식 우선권")
                        .amount(80)
                        .state(0)
                        .build()
        );
        consumptionRepository.save(
                Consumption.builder()
                        .nation(nationTest)
                        .content("청소 1회 면제")
                        .amount(100)
                        .state(0)
                        .build()
        );
    }

    @Test
    void consumptionStudentInsert(){

        NationStudent nationStudent = nationStudentRepository.findById(1L).orElse(null);

        consumptionNationStudentRepository.save(
                ConsumptionNationStudent.builder()
                        .consumption(consumptionRepository.findById(2L).orElse(null))
                        .nationStudent(nationStudent)
                        .state(0)
                        .build()
        );

        consumptionNationStudentRepository.save(
                ConsumptionNationStudent.builder()
                        .consumption(consumptionRepository.findById(1L).orElse(null))
                        .nationStudent(nationStudent)
                        .state(0)
                        .build()
        );

        consumptionNationStudentRepository.save(
                ConsumptionNationStudent.builder()
                        .consumption(consumptionRepository.findById(5L).orElse(null))
                        .nationStudent(nationStudent)
                        .state(0)
                        .build()
        );

        consumptionNationStudentRepository.save(
                ConsumptionNationStudent.builder()
                        .consumption(consumptionRepository.findById(4L).orElse(null))
                        .nationStudent(nationStudent)
                        .state(1)
                        .build()
        );

        consumptionNationStudentRepository.save(
                ConsumptionNationStudent.builder()
                        .consumption(consumptionRepository.findById(2L).orElse(null))
                        .nationStudent(nationStudent)
                        .state(2)
                        .build()
        );

        consumptionNationStudentRepository.save(
                ConsumptionNationStudent.builder()
                        .consumption(consumptionRepository.findById(1L).orElse(null))
                        .nationStudent(nationStudent)
                        .state(3)
                        .build()
        );

    }
}
