package Exceptions;

public class InvalidTargetException extends RuntimeException {
    public InvalidTargetException() {
    }

    public InvalidTargetException(String the_target_i_already_filled_) {
    }

    public InvalidTargetException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidTargetException(Throwable cause) {
        super(cause);
    }

    public InvalidTargetException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
