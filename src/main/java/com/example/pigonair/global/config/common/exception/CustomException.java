package com.example.pigonair.global.config.common.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException{
    private final ErrorCode errorCode;
    public String getKey(){
        return errorCode.getKey();
    }
    @Override
    public String getMessage() {
        return errorCode.getMessage();
    }
    public HttpStatus getHttpStatus(){
        return errorCode.getHttpStatus();
    }
}