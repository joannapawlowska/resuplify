package components.exceptions;

public class APICallException extends RuntimeException {

    private int statusCode;

    public APICallException(String message) {
        super(message);
    }

    public APICallException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}