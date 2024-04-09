package Exceptions;

public class TargetOutOfBoundException extends RuntimeException {
    public TargetOutOfBoundException() {
    }

    public TargetOutOfBoundException(String s) {
    }

    public TargetOutOfBoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TargetOutOfBoundException(Throwable cause) {
        super(cause);
    }

    public TargetOutOfBoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
