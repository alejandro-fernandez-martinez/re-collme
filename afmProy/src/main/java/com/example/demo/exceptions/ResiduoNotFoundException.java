package com.example.demo.exceptions;

public class ResiduoNotFoundException extends RuntimeException {
    public ResiduoNotFoundException(Long id) {
        super("No se puede encontrar el residuo con ID: " + id);
    }
}
