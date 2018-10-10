package com.demo.microservice.recommendation.controller;

import com.demo.microservice.recommendation.boundary.ApiResponse;
import com.demo.microservice.recommendation.exception.CustomerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.Objects;

@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ApiResponse> handleCustomerNotFoundException(CustomerNotFoundException e) {
        String responseDescription = null ;
        if(Objects.isNull(e.getMessage())){
            responseDescription = "Entity Not Exist";
        }
        else{
            responseDescription = e.getMessage();
        }
        ApiResponse apiResponse = new ApiResponse(responseDescription);
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ApiResponse> handleIOException(IOException e) {
        ApiResponse apiResponse = new ApiResponse("File Upload Error");
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
