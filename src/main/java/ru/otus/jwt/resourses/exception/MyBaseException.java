package ru.otus.jwt.resourses.exception;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MyBaseException extends RuntimeException {
    private final Logger logger = Logger.getLogger(getClass().getName());

    public MyBaseException(String message, Exception e) {
        super(message);
        logger.log(Level.SEVERE, message, e);
    }

    public MyBaseException(String message) {
        super(message);
    }
}
