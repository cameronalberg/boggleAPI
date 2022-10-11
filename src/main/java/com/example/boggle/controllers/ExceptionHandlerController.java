package com.example.boggle.controllers;


import com.example.boggle.response.ApiError;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.webjars.NotFoundException;

import javax.servlet.http.HttpServletRequest;

@RestController
@ControllerAdvice
public class ExceptionHandlerController implements ErrorController {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @GetMapping("/error")
    ResponseEntity<Object> handleNotFoundException() {
        ApiError error = new ApiError("not found.");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    ResponseEntity<Object> handleMissingParameterException(HttpServletRequest request) {
        String message = "missing request parameter";
        if (request.getServletPath().contains("/solve")) {
            message += " (board)";
        }
        message += ".";
        return new ResponseEntity<>(new ApiError(message), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    ResponseEntity<Object> handleTypeMisMatchError(HttpServletRequest request) {
        String message = "could not parse request parameter";
        if (request.getServletPath().contains("/shuffle")) {
            message += " (size must be integer)";
        }
        message += ".";
        return new ResponseEntity<>(new ApiError(message), HttpStatus.BAD_REQUEST);
    }
}
