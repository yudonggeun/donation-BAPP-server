package com.bapp.donationserver.controller;

import com.bapp.donationserver.data.status.Status;
import com.bapp.donationserver.exception.IllegalUserDataException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalUserDataException.class)
    public Status badRequestHandle(Exception e) {
        log.info("error msg = {}", e.getMessage());
        return Status.failStatus(e.getMessage());
    }
}
//기능 적용하지 않은 클래스 수정