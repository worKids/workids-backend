package com.workids.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ExceptionEnum {
    RUNTIME_EXCEPTION(HttpStatus.BAD_REQUEST, "E0001", "내부 문제로 다음번에 다시 시도해주세요."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E0002", "내부 문제로 다음번에 다시 시도해주세요."),
    API_NOT_EXIST_EXCEPTION(HttpStatus.NOT_FOUND, "E0003", "존재하지 않는 API 입니다."),
    API_METHOD_NOT_ALLOWED_EXCEPTION(HttpStatus.METHOD_NOT_ALLOWED, "E0004", "지원하지 않는 Method 입니다."),
    API_PARAMETER_EXCEPTION(HttpStatus.BAD_REQUEST, "E0005", "파라미터 타입과 값을 확인하세요."),
    MEMBER_ACCESS_EXCEPTION(HttpStatus.FORBIDDEN, "M0001", "접근 권한이 없습니다."),
    NATION_NOT_EXIST_EXCEPTION(HttpStatus.NOT_FOUND, "N0001", "나라가 존재하지 않습니다."),
    TEACHER_NOT_MATCH_EXCEPTION(HttpStatus.CREATED, "T0001", "선생님이 일치하지 않습니다.."),
    STUDENT_NOT_MATCH_EXCEPTION(HttpStatus.CREATED, "S0001", "학생과 일치하지 않습니다."),
    SERVER_NOT_CONNECT_EXCEPTION(HttpStatus.SERVICE_UNAVAILABLE, "S0001", "서비스가 연결되지 않았습니다."), 

    // 은행
    BANK_NOT_EXIST_EXCEPTION(HttpStatus.NOT_FOUND, "B0001", "해당 은행 상품이 존재하지 않습니다."); 

    private final HttpStatus status;
    private final String code;
    private final String message;
}