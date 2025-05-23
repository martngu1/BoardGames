package no.ntnu.idatg2003.mappe10.exceptions;

public class BoardParsingException extends RuntimeException {
  public BoardParsingException(String message) {
    super(message);
  }

  public BoardParsingException(String message, Throwable cause) {
    super(message, cause);
  }
}
