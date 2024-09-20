package com.uexcel.eazyschool.rest;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;

@RestControllerAdvice
@Order(1)
public class HandleExceptionController extends ResponseEntityExceptionHandler {
    @ExceptionHandler(CustomExceptionHandler.class)
    public ResponseEntity<Response> handleException(CustomExceptionHandler ex) {
        return ResponseEntity.status(ex.getStatusCode())
                .body(new Response(ex.getStatusCode(),ex.getMsg()));

    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        return new ResponseEntity<>(new Response(status.value(),
                Objects.requireNonNull(ex.getFieldError()).getDefaultMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> exception(Exception ex, WebRequest request) {
        return new ResponseEntity<>(new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
