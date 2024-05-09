package com.jetdevs.test.fileuploadservices.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CustomResponseHandler {

    @ExceptionHandler(value = {Exception.class, RuntimeException.class})
    public ResponseEntity<Map<Object, Object>> defaultErrorHandler(HttpServletRequest req, Exception e) {
        Map<Object, Object> response = new HashMap<>();
        response.put("error", e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = ResponseStatusException.class)
    public ResponseEntity<Map<Object, Object>> restException(HttpServletRequest req, ResponseStatusException e) {
        Map<Object, Object> response = new HashMap<>();
        response.put("error", e.getMessage());
        return new ResponseEntity<>(response, e.getStatusCode());
    }

}
