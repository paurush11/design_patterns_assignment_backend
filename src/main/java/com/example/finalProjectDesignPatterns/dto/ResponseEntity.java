package com.example.finalProjectDesignPatterns.dto;

import lombok.Data;

@Data
public class ResponseEntity<E> {

    private String message;

    private E data;

    private ReturnType responseStatus;


    public ResponseEntity() {
    }

    public ResponseEntity(String message, E data, ReturnType responseStatus) {
        this.message = message;
        this.data = data;
        this.responseStatus = responseStatus;
    }
}
