package traffic_id.demo.exceptions;

public class EmailAlreadyExistException extends Exception {
    public EmailAlreadyExistException() { super(); }
    public EmailAlreadyExistException(String message) { super(message); }
    public EmailAlreadyExistException(String message, Throwable cause) { super(message, cause); }
    public EmailAlreadyExistException(Throwable cause) { super(cause); }
  }