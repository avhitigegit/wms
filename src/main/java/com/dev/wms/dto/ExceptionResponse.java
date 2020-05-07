package com.dev.wms.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class ExceptionResponse implements Serializable {
    private Integer statusCode;
    private String errorMessage;
    private String errorUrl;
    private LocalDateTime timestamp;
}
