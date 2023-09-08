package com.workids.userTest;

import com.workids.domain.Student;
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
public class UserTest {

    @Autowired
    StudentRepository studentRepository;
    @Test
    void studentInsert() {
        Student student = Student.builder()
                .id("a1")
                .password("12")
                .name("홍길동1")
                .email("ab1@naver.com")
                .phone("010-1111-1112")
                .registNumber("001111-1111111")
                .state(0)
                .build();
        studentRepository.save(student);
        Assertions.assertThat(student.getId()).isEqualTo("a1");
    }


}
