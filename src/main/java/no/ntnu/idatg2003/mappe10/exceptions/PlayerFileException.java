package no.ntnu.idatg2003.mappe10.exceptions;

public class PlayerFileException extends RuntimeException {
    public PlayerFileException(String message) {
        super(message);
    }

    public PlayerFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
