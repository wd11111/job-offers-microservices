package com.wd.exceptionhandler.model;

import org.springframework.http.HttpStatus;

public record ErrorResponse(String message,
                            HttpStatus httpStatus) {
}
