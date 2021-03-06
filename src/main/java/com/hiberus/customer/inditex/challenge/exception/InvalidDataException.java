package com.hiberus.customer.inditex.challenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidDataException extends Exception{

    private static final long serialVersionUID = 1L;

    public InvalidDataException(String message){
        super(message);
    }
}
