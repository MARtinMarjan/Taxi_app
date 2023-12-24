package com.example.taxi_app_final.model.exceptions;

public class InvalidUserCredentialException extends RuntimeException {

    public InvalidUserCredentialException() {
        super("Invalid user credentials exception");
    }
}
