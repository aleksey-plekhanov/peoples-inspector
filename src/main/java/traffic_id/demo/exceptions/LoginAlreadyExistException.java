package traffic_id.demo.exceptions;

public class LoginAlreadyExistException extends Exception {
    public LoginAlreadyExistException() { super(); }
    public LoginAlreadyExistException(String message) { super(message); }
    public LoginAlreadyExistException(String message, Throwable cause) { super(message, cause); }
    public LoginAlreadyExistException(Throwable cause) { super(cause); }
}
