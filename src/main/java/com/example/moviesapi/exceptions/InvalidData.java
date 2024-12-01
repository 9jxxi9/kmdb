package com.example.moviesapi.exceptions;

public class InvalidData extends RuntimeException {
    public InvalidData(String message) {
        super(message);
    }
}