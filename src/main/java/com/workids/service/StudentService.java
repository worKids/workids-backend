package com.workids.service;

import com.workids.domain.Student;
import com.workids.dto.request.JoinDto;
import com.workids.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author nwk66
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    /**
     * 회원 가입
     */
    @Transactional
    public void join(JoinDto dto) {
        Student student = Student.of(dto);
        System.out.println("service 들어옴");
        System.out.println("student = " + student);
        studentRepository.save(student);
        System.out.println("회원가입 완료");
    }
}
