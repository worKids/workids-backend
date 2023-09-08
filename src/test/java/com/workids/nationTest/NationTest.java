package com.workids.nationTest;

import com.workids.domain.Nation;
import com.workids.domain.Student;
import com.workids.domain.Teacher;
import com.workids.repository.NationRepository;
import com.workids.repository.StudentRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;

@SpringBootTest
@Commit
@Transactional
public class NationTest {

    @Autowired
    NationRepository nationRepository;


    @Test
    void nationInsert() {
        Teacher teacherTest = new Teacher(1L, "b1", "12", "김동글1", "ab1@naver.com", "010-1111-1112", 1, null);


        Nation nation = Nation.builder()
                .teacher(teacherTest)
                .name("일다수")
                .moneyName("미소")
                .taxRate(5)
                .presidentName("김동글1")
                .code("abcd1111")
                .school("동글")
                .grade(1)
                .classRoom(1)
                .payDay(1)
                .state(1)
                .build();
        nationRepository.save(nation);
        Assertions.assertThat(nation.getName()).isEqualTo("일다수");
    }


}
