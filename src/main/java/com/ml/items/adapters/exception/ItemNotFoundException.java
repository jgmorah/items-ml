package com.ml.items.adapters.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ItemNotFoundException extends ResponseStatusException {

    public ItemNotFoundException(String message) {
        super(HttpStatus.BAD_REQUEST,message);

    }
}
