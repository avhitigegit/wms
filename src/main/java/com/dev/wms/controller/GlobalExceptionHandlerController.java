package com.dev.wms.controller;

import com.dev.wms.dto.ExceptionResponse;
import com.dev.wms.exception.*;
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

    //Server
    @ResponseBody
    @ExceptionHandler(BadResponseException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse badResponseException(final BadResponseException exception, final HttpServletRequest request) {
        ExceptionResponse error = new ExceptionResponse();
        error.setErrorMessage(exception.getMessage());
        error.setErrorUrl(request.getRequestURI());
        error.setStatusCode(500);
        error.setTimestamp(now);
        return error;
    }

    @ResponseBody
    @ExceptionHandler(ExpiredTokenException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ExceptionResponse expiredTokenException(final ExpiredTokenException exception, final HttpServletRequest request) {
        ExceptionResponse error = new ExceptionResponse();
        error.setErrorMessage(exception.getMessage());
        error.setErrorUrl(request.getRequestURI());
        error.setStatusCode(401);
        error.setTimestamp(now);
        return error;
    }

    @ResponseBody
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ExceptionResponse unauthorizedException(final UnauthorizedException exception, final HttpServletRequest request) {
        ExceptionResponse error = new ExceptionResponse();
        error.setErrorMessage(exception.getMessage());
        error.setErrorUrl(request.getRequestURI());
        error.setStatusCode(401);
        error.setTimestamp(now);
        return error;
    }

    @ResponseBody
    @ExceptionHandler(EmailSendingException.class)
    @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
    public ExceptionResponse emailSendingException(final EmailSendingException exception, final HttpServletRequest request) {
        ExceptionResponse error = new ExceptionResponse();
        error.setErrorMessage(exception.getMessage());
        error.setErrorUrl(request.getRequestURI());
        error.setStatusCode(503);
        error.setTimestamp(now);
        return error;
    }
}
