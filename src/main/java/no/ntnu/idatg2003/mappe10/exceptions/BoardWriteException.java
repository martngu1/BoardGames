package no.ntnu.idatg2003.mappe10.exceptions;

public class BoardWriteException extends RuntimeException {
    public BoardWriteException(String message) {
        super(message);
    }

    public BoardWriteException(String message, Throwable cause) {
        super(message, cause);
    }
}
