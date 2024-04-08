package Exceptions;

public class InvalidPlayerSymbolsException extends RuntimeException {
    public InvalidPlayerSymbolsException() {
    }

    public InvalidPlayerSymbolsException(String message) {
        super(message);
    }

    public InvalidPlayerSymbolsException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPlayerSymbolsException(Throwable cause) {
        super(cause);
    }

    public InvalidPlayerSymbolsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
