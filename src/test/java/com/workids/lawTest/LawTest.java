package com.workids.lawTest;

import com.workids.domain.Law;
import com.workids.domain.Nation;
import com.workids.repository.LawRepository;
import com.workids.repository.NationRepository;
import org.assertj.core.api.Assertions;
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
    NationRepository nationRepository;

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
        Assertions.assertThat(law.getContent()).isEqualTo("숙제 1회 안함");
    }

}
