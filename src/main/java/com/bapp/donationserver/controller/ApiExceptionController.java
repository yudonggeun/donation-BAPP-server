package com.bapp.donationserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiExceptionController {

    /*@ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler//(IllegalArgumentException.class)
    public ErrorResult badRequestHandle(Exception e) {
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("BAD_REQUEST", e.getMessage());
    }*/
}

//기능 적용하지 않은 클래스 수정