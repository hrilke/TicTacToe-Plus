package Exceptions;

public class InvalidBoardSizeException extends RuntimeException {
    public InvalidBoardSizeException(String message) {
        super(message);
    }
}
