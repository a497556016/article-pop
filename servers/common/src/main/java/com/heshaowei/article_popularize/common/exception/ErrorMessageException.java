package com.heshaowei.article_popularize.common.exception;

public class ErrorMessageException extends RuntimeException {
    public ErrorMessageException() {
    }

    public ErrorMessageException(String message) {
        super(message);
    }

    public ErrorMessageException(String message, Throwable cause) {
        super(message, cause);
    }

    public ErrorMessageException(Throwable cause) {
        super(cause);
    }
}
