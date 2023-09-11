package com.workids.lawTest;

import com.workids.domain.law.entity.Law;
import com.workids.domain.law.entity.LawNationStudent;
import com.workids.domain.law.repository.LawNationStudentRepository;
import com.workids.domain.law.repository.LawRepository;
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
public class LawTest {

    @Autowired
    LawRepository lawRepository;

    @Autowired
    LawNationStudentRepository lawNationStudentRepository;

    @Autowired
    NationRepository nationRepository;

    @Autowired
    NationStudentRepository nationStudentRepository;

    @Test
    void lawInsert(){

        Nation nationTest = nationRepository.findById(1L).orElse(null);

        Law law = Law.builder()
                .nation(nationTest)
                .content("숙제 1회 안함")
                .type(0)
                .fine(50)
                .state(0)
                .build();

        lawRepository.save(law);
        lawRepository.save(
                Law.builder()
                        .nation(nationTest)
                        .content("지각 할 시")
                        .type(0)
                        .fine(80)
                        .state(0)
                        .build()
        );

        lawRepository.save(
                Law.builder()
                        .nation(nationTest)
                        .content("친구를 때릴 시")
                        .type(0)
                        .fine(120)
                        .state(0)
                        .build()
        );
        lawRepository.save(
                Law.builder()
                        .nation(nationTest)
                        .content("수업시간 중 잡담할 시")
                        .type(1)
                        .penalty("청소 3회 실시")
                        .state(0)
                        .build()
        );
        lawRepository.save(
                Law.builder()
                        .nation(nationTest)
                        .content("욕할 시")
                        .type(1)
                        .penalty("칠판 청소 3회 실시")
                        .state(0)
                        .build()
        );
    }

    @Test
    void lawNationStudentInsert(){

        //insert into nation_student(nation_student_num, student_num, nation_num, citizen_number, credit_rating, student_name, state) values(1,1,1,1,50,'홍길동1',0);


        NationStudent nationStudent = nationStudentRepository.findById(1L).orElse(null);
        lawNationStudentRepository.save(
                LawNationStudent.builder()
                        .law(lawRepository.findById(1L).orElse(null))
                        .nationStudent(nationStudent)
                        .build()
        );
        lawNationStudentRepository.save(
                LawNationStudent.builder()
                        .law(lawRepository.findById(3L).orElse(null))
                        .nationStudent(nationStudent)
                        .build()
        );
        lawNationStudentRepository.save(
                LawNationStudent.builder()
                        .law(lawRepository.findById(4L).orElse(null))
                        .nationStudent(nationStudent)
                        .penaltyCompleteState(0)
                        .build()
        );

    }

}
