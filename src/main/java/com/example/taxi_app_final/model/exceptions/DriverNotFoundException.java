package com.example.taxi_app_final.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DriverNotFoundException extends RuntimeException{
    public DriverNotFoundException(Long id){
        super("Driver not found");
    }
}
