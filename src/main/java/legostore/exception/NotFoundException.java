package legostore.exception;

public class NotFoundException extends LegoStoreException {
    public NotFoundException(String message) {
        super(message);
    }
    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
