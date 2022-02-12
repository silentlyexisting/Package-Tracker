package com.tracker.code.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PackageNotFoundException extends RuntimeException {
    public PackageNotFoundException(long id) {
        super("Package with number:" + id + " not found");
    }
}
