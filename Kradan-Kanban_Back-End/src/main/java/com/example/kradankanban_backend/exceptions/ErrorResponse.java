package com.example.kradankanban_backend.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Getter
@Setter
@Data
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse{
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss'Z'" , timezone = "UTC")
//    private LocalDateTime timestamp;
//    private int status;
//    private String message;
//    private String instance;
//
//    public ErrorResponse(int status, String message, String instance) {
//        this.timestamp = LocalDateTime.now();
//        this.status = status;
//        this.message = message;
//        this.instance = instance;
//    }

    private final int status;
    private final String message;
    private final String instance;
    private String title;
    private String stackTrace;
    private final LocalDateTime timestamp = LocalDateTime.now();
    private List<ValidationError> errors;

    @Getter
    @Setter
    @RequiredArgsConstructor
    private static class ValidationError {
        private final String field;
        private final String message;
    }

    public void addValidationError(String field, String message) {
        if (Objects.isNull(errors)) {
            errors = new ArrayList<>();
        }
        errors.add(new ValidationError(field, message));
    }
}
