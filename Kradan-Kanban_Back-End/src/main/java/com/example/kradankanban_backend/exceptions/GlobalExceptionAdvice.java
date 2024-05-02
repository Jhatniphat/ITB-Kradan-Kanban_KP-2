package com.example.kradankanban_backend.exceptions;

import com.example.kradankanban_backend.controllers.TaskController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice(assignableTypes = TaskController.class)
public class GlobalExceptionAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleItemNotFoundException(ItemNotFoundException ex, WebRequest request) {
        return errorResponse(ex.getMessage(), HttpStatus.NOT_FOUND, request);
    }

    private ResponseEntity<ErrorResponse> errorResponse(String message, HttpStatus status, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(status.value(), message, request.getDescription(false).replace("uri=", ""));
        return ResponseEntity.status(status).body(errorResponse);
    }

}
