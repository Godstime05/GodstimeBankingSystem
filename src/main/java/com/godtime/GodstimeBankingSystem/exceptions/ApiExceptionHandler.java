package com.godtime.GodstimeBankingSystem.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = ApiRequestException.class)
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException apiRequestException) {
        ApiException apiException = new ApiException(
                apiRequestException.getMessage(),
                apiRequestException,
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(
                apiException,
                apiException.getHttpStatus()
        );
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException notFoundException) {
        ApiException apiException = new ApiException(
                notFoundException.getMessage(),
                notFoundException,
                HttpStatus.NOT_FOUND,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(
                apiException,
                apiException.getHttpStatus()
        );
    }

    @ExceptionHandler(value = EmployeeNotFoundInBranchException.class)
    public ResponseEntity<Object> handleEmployeeNotFoundInBranch(EmployeeNotFoundInBranchException employeeNotFoundInBranch) {
        ApiException apiException = new ApiException(
                employeeNotFoundInBranch.getMessage(),
                employeeNotFoundInBranch,
                HttpStatus.NOT_FOUND,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(
                apiException,
                apiException.getHttpStatus()
        );
    }
}

