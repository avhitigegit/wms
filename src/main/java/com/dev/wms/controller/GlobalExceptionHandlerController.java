package com.dev.wms.controller;

import com.dev.wms.dto.ExceptionResponse;
import com.dev.wms.exception.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandlerController {
    LocalDateTime now = LocalDateTime.now();

    //Client
    @ResponseBody
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ExceptionResponse badRequestException(final BadRequestException exception, final HttpServletRequest request) {
        ExceptionResponse error = new ExceptionResponse();
        error.setErrorMessage(exception.getMessage());
        error.setErrorUrl(request.getRequestURI());
        error.setStatusCode(400);
        error.setTimestamp(now);
        return error;
    }
}
