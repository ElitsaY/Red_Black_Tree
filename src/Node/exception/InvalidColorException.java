package Node.exception;

public class InvalidColorException extends RuntimeException {

    public InvalidColorException(String message) {
        super(message);
    }

    public InvalidColorException(String message, Throwable cause) {
        super(message, cause);
    }
}
