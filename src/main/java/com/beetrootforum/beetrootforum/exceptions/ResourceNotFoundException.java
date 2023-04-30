package com.beetrootforum.beetrootforum.exceptions;

public class ResourceNotFoundException extends NotFoundException {

    public ResourceNotFoundException(String message) {
        super("Resource not found: " + message);
    }
}
