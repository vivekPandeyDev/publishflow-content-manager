package com.publishflow.exception;

import lombok.Getter;

import java.io.Serial;

@Getter
public class RestClientException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 473536011764154409L;

    public RestClientException(String message) {
        super(message);
    }

}