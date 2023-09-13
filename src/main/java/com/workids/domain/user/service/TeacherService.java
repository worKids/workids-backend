package com.workids.domain.user.service;

import com.workids.domain.user.dto.request.LoginDto;
import com.workids.domain.user.dto.request.TeacherJoinDto;
import com.workids.domain.user.entity.Student;
import com.workids.domain.user.entity.Teacher;
import com.workids.domain.user.repository.TeacherRepository;
import com.workids.global.exception.ApiException;
import com.workids.global.exception.ExceptionEnum;
import com.workids.global.security.JwtTokenProvider;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final PasswordEncoder passwordEncoder;

    @NonNull
    private PasswordEncoder encoder;

    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 회원 가입
     */
    @Transactional
    public void join(TeacherJoinDto joinDto){
        if (teacherRepository.findById(joinDto.getId()).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        if (teacherRepository.findByEmail(joinDto.getEmail()).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }


        Teacher teacher = teacherRepository.save(Teacher.of(joinDto));
        teacher.encodePassword(passwordEncoder);
        teacher.addUserAuthority();

        System.out.println("회원가입 완료");
    }

    /**
     * 로그인(spring security + jwt)
     */
    public String login(LoginDto loginDto){

        // 가입 여부 확인
        Teacher teacher = teacherRepository.findById(loginDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 id 입니다."));

        // 비밀번호 일치 확인
        if(!checkPassword(loginDto.getPassword(), teacher.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        List<String> roles = new ArrayList<>();
        roles.add(teacher.getRole().name());

        System.out.println("로그인 성공");
        // 생성된 토큰 반환
        return jwtTokenProvider.createToken(Long.toString(teacher.getTeacherNum()), roles);


    }

    /**
     * 로그인 시 선생님 이름 가져오기
     * @param teacherNum
     * @return
     */
    public String getTeacherName(String teacherNum) {
        Teacher teacher = teacherRepository.findById(Long.parseLong(teacherNum))
                .orElseThrow(() -> new ApiException(ExceptionEnum.TEACHER_NOT_MATCH_EXCEPTION));
        return teacher.getName();
    }
    /** 비밀번호 일치 확인 **/
    public boolean checkPassword(String password, String dbPassword) {
        return encoder.matches(password, dbPassword);
    }
}
