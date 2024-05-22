package com.example.demo.exceptions;

import org.springframework.lang.Nullable;

public class NotFoundException extends IllegalArgumentException {

    public NotFoundException(@Nullable String message) {
        super(message);
    }
}
