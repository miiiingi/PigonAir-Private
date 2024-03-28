package com.example.pigonair.global.config.common.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.pigonair.global.config.common.dto.response.ResponseDto;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handelCustomException(CustomException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(ResponseDto.error(e.getKey(),e.getMessage(),e.getHttpStatus()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleSQLException(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        bindingResult.getAllErrors().forEach(e -> errors.put(((FieldError)e).getField() , e.getDefaultMessage()));
        return ResponseEntity.badRequest().body(
                new ResponseDto<>(ErrorCode.VALIDATION_ERROR.getKey(),
                                ErrorCode.VALIDATION_ERROR.getMessage(),
                                errors)
        );
    }
}