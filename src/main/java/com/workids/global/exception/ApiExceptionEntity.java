package com.workids.global.exception;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiExceptionEntity {
    private String code;
    private String message;

    @Override
    public String toString() {
        return "ApiExceptionEntity [errorCode=" + code + ", errorMessage=" + message + "]";
    }

}