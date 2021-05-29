package components.exceptions;

public class APICallException extends RuntimeException {

    public APICallException(String message) {
        super(message);
    }
}