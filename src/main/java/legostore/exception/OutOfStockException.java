package legostore.exception;

public class OutOfStockException extends LegoStoreException {
    public OutOfStockException(String message) {
        super(message);
    }
    public OutOfStockException(String message, Throwable cause) {
        super(message, cause);
    }
}
