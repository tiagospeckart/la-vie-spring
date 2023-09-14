package com.moredevs.psychclinic.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason = "The parameters are wrong")
public class WrongParametersException extends RuntimeException {

    public WrongParametersException(){
        super("The parameters are wrong");
    }
}