package com.workids.userTest;

import com.workids.domain.user.entity.Teacher;
import com.workids.domain.user.repository.TeacherRepository;
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
public class TeacherTest {

    @Autowired
    TeacherRepository teacherRepository;
    @Test
    void teacherInsert() {
        Teacher teacher = Teacher.builder()
                .id("b1")
                .password("12")
                .name("김동글1")
                .email("ab1@naver.com")
                .phone("010-1111-1112")
                .state(UserStateType.ACTIVE)
                .role(Role.TEACHER)
                .build();
        teacherRepository.save(teacher);
        Assertions.assertThat(teacher.getId()).isEqualTo("b1");
    }


}
