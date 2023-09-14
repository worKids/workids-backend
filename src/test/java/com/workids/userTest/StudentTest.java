package com.workids.userTest;

import com.workids.domain.user.entity.Student;
import com.workids.domain.user.repository.StudentRepository;
import com.workids.global.config.Role;
import com.workids.global.config.stateType.UserStateType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;

@SpringBootTest
@Commit
@Transactional
public class StudentTest {
    @Autowired
    StudentRepository studentRepository;

    @Test
    void studentInsert(){
        Student student = Student.builder()
                .id("a1")
                .password("12")
                .name("홍길동1")
                .email("ab1@naver.com")
                .phone("010-1111-1112")
                .state(UserStateType.ACTIVE)
                .role(Role.STUDENT)
                .registNumber("001111-11111111")
                .build();
        studentRepository.save(student);
        Assertions.assertThat(student.getId()).isEqualTo("a1");
    }
}
