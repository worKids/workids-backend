package com.workids.domain.user.service;

import com.workids.domain.user.entity.Student;
import com.workids.domain.user.dto.request.StudentJoinDto;
import com.workids.domain.user.dto.request.LoginDto;
import com.workids.global.exception.ApiException;
import com.workids.global.exception.ExceptionEnum;
import com.workids.global.security.JwtTokenProvider;
import com.workids.domain.user.repository.StudentRepository;
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
public class StudentService {

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    @NonNull
    private PasswordEncoder encoder;

    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 회원 가입
     */
    @Transactional
    public void join(StudentJoinDto joinDto){
        if (studentRepository.findById(joinDto.getId()).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        if (studentRepository.findByRegistNumber(joinDto.getRegistNumber()).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 주민등록번호입니다.");
        }


        Student student = studentRepository.save(Student.of(joinDto));
        student.encodePassword(passwordEncoder);
        student.addUserAuthority();

        System.out.println("회원가입 완료");
    }

    /**
     * 로그인(spring security + jwt)
     */
    public String login(LoginDto loginDto){

        // 가입 여부 확인
        Student student = studentRepository.findById(loginDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 id 입니다."));

        // 비밀번호 일치 확인
        if(!checkPassword(loginDto.getPassword(), student.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        List<String> roles = new ArrayList<>();
        roles.add(student.getRole().name());

        System.out.println("로그인 성공");
        // 생성된 토큰 반환
        return jwtTokenProvider.createToken(Long.toString(student.getStudentNum()), roles);


    }

    /**
     * 로그인 시 학생 이름 가져오기
     * @param studentNum
     * @return
     */
    public String getStudentName(String studentNum) {
        Student student = studentRepository.findById(Long.parseLong(studentNum))
                .orElseThrow(() -> new ApiException(ExceptionEnum.STUDENT_NOT_MATCH_EXCEPTION));
        return student.getName();
    }

    /** 비밀번호 일치 확인 **/
    public boolean checkPassword(String password, String dbPassword) {
        return encoder.matches(password, dbPassword);
    }
}
