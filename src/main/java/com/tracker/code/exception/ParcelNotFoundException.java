package com.tracker.code.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ParcelNotFoundException extends RuntimeException {
    public ParcelNotFoundException(long id) {
        super("Parcel with number:" + id + " not found");
    }
}
