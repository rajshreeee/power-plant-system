package com.virtual.powerplant.exceptionhandlers;

import com.virtual.powerplant.error.CustomError;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class ValidationExceptionHandler implements IExceptionHandler<ConstraintViolationException, ResponseEntity<CustomError>> {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResponseEntity<CustomError> handle(final ConstraintViolationException exception,
                                               HttpServletRequest request) {
        log.error("Validation error occurred.");
        String validationMsg = exception.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(StringUtils.SPACE));
        return new ResponseEntity<>(new CustomError(validationMsg, request.getServletPath()), HttpStatus.BAD_REQUEST);
    }
}