package com.kepler.rominfo.exception;

public class CourseAlreadyExistsException extends Exception {
    public CourseAlreadyExistsException() {}

    public CourseAlreadyExistsException(String message) {
        super(message);
    }
}
