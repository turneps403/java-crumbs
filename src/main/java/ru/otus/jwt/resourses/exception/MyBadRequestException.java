package ru.otus.jwt.resourses.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MyBadRequestException extends ru.otus.jwt.resourses.exception.MyBaseException {
    public MyBadRequestException(String message, Exception e) {
        super(message, e);
    }

    public MyBadRequestException(String message) {
        super(message);
    }
}
