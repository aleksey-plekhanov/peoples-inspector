package traffic_id.demo.exceptions;

public class PasswordIncorrectException extends Exception {
    public PasswordIncorrectException() { super(); }
    public PasswordIncorrectException(String message) { super(message); }
    public PasswordIncorrectException(String message, Throwable cause) { super(message, cause); }
    public PasswordIncorrectException(Throwable cause) { super(cause); }
}
