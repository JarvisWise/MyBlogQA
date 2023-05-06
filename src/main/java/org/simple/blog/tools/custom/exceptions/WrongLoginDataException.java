package org.simple.blog.tools.custom.exceptions;

public class WrongLoginDataException extends Exception {
    public WrongLoginDataException(String errorMessage) {
        super(errorMessage);
    }

    public WrongLoginDataException(Throwable err) {
        super(err);
    }

    public WrongLoginDataException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
