package ru.rosbank.javaschool.exception;

public class DataSaveException extends RuntimeException {
    public DataSaveException() {
    }

    public DataSaveException(String message) {
        super(message);
    }

    public DataSaveException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataSaveException(Throwable cause) {
        super(cause);
    }

    public DataSaveException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
