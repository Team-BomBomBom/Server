package com.bombombom.devs.global.exception;

import java.time.LocalDateTime;

public record ErrorResponse(LocalDateTime timestamp, int status, String message) {

    public ErrorResponse(int status, String message) {
        this(LocalDateTime.now(), status, message);
    }
}
