package legostore.exception;

public class LegoStoreException extends RuntimeException {
    public LegoStoreException(String message) {
        super(message);
    }
    public LegoStoreException(String message, Throwable cause) {
        super(message, cause);
    }
}
