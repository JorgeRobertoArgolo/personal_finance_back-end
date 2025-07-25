package com.jorgerobertoargolo.personal_finance.infrastructure.exception;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ValidationExceptionDetails {
    private LocalDateTime timestamp;
    private String title;
    private String details;
    private String developerMessage;
    private String fields;
    private String fieldsMessage;
}
