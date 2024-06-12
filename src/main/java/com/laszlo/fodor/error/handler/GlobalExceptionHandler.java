package com.laszlo.fodor.error.handler;

import com.laszlo.fodor.error.dto.ErrorMessageDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(Exception ex, WebRequest request) {
        ErrorMessageDTO response = ErrorMessageDTO.builder()
                .message(((MethodArgumentNotValidException) ex).getDetailMessageArguments()[1].toString())
                .status(((MethodArgumentNotValidException) ex).getStatusCode().value())
                .build();
        return new ResponseEntity<>(response, HttpStatus.resolve(response.getStatus()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}