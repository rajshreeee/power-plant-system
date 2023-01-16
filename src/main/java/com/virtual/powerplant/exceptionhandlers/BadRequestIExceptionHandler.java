package com.virtual.powerplant.exceptionhandlers;

import com.virtual.powerplant.error.CustomError;
import com.virtual.powerplant.exceptions.BadRequestException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class BadRequestIExceptionHandler implements IExceptionHandler<BadRequestException, ResponseEntity<CustomError>> {

    @ExceptionHandler(BadRequestException.class)
    @ResponseBody
    public ResponseEntity<CustomError> handle(final BadRequestException exception, final HttpServletRequest request) {
        log.error("Bad Request");
        return new ResponseEntity<>(
                new CustomError(exception.getMessage(),
                        request.getServletPath()),
                HttpStatus.BAD_REQUEST);
    }
}