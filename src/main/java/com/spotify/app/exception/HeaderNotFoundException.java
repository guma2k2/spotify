package com.spotify.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class HeaderNotFoundException extends RuntimeException {
    public HeaderNotFoundException(String message) {
        super(message);
    }
}
