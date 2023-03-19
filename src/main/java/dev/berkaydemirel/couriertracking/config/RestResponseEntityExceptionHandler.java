package dev.berkaydemirel.couriertracking.config;

import dev.berkaydemirel.couriertracking.controller.response.Response;
import dev.berkaydemirel.couriertracking.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Response<?>> handleNotFoundException(NotFoundException e, HttpServletRequest request) {
        log.error("An not found error occurred when processing url: {} error message: {}", request.getRequestURL(), e.getMessage(), e);
        return new ResponseEntity<>(new Response<>(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response<?>> handle(MethodArgumentNotValidException exception) {
        Map<String, String> validationErrors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(fieldError -> {
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        return new ResponseEntity<>(new Response<>("Method argument not valid!", validationErrors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Response<?>> handleOtherExceptions(Exception e, HttpServletRequest request) {
        log.error("An error occurred when processing url: {} error message: {}", request.getRequestURL(), e.getMessage(), e);
        return new ResponseEntity<>(new Response<>("Internal Server Error!"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
