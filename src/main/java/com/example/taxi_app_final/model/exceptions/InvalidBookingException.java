package com.example.taxi_app_final.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)

public class InvalidBookingException extends RuntimeException{
    public InvalidBookingException(Long id){
        super(String.format("Booking with id %d not found",id));
    }
}
