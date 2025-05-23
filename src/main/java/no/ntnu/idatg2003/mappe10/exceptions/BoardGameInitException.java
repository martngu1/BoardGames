package no.ntnu.idatg2003.mappe10.exceptions;

public class BoardGameInitException extends RuntimeException {
  public BoardGameInitException(String message) {
    super(message);
  }

  public BoardGameInitException(String message, Throwable cause) {
    super(message, cause);
  }

}
