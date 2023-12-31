package com.workids.nationTest;

import com.workids.domain.nation.entity.Nation;
import com.workids.domain.nation.repository.NationRepository;
import com.workids.domain.user.dto.request.TeacherJoinDto;
import com.workids.domain.user.entity.Teacher;
import com.workids.global.config.Role;
import com.workids.global.config.stateType.NationStateType;
import com.workids.global.config.stateType.UserStateType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@SpringBootTest
@Commit
@Transactional
public class NationTest {

    @Autowired
    NationRepository nationRepository;


    @Test
    void nationInsert() {
        Teacher teacherTest =
                new Teacher(1L, "b1", "12", "김동글1", "ab1@naver.com", "010-1111-1112", UserStateType.ACTIVE, Role.TEACHER);

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
                .state(NationStateType.IN_OPERATE)
                .startDate(LocalDateTime.of(2023, 9, 1, 0, 0, 0))
                .endDate(LocalDateTime.of(2023, 9, 22, 23, 59, 59))
                .build();
        nationRepository.save(nation);
        Assertions.assertThat(nation.getName()).isEqualTo("일다수");
    }
}
