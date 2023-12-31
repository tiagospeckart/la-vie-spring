package com.moredevs.psychclinic.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "The object was not found")
public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        super("The object was not found");
    }
}