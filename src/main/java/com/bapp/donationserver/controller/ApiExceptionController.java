package com.bapp.donationserver.controller;

import com.bapp.donationserver.data.status.Status;
import com.bapp.donationserver.exception.IllegalUserDataException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.NoResultException;

@Slf4j
@RestControllerAdvice
public class ApiExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalUserDataException.class)
    public Status badRequestHandle(Exception e) {
        return Status.failStatus(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoResultException.class)
    public Status NoResult(Exception e) {
        return Status.failStatus("데이터가 존재하지 않습니다.");
    }
}