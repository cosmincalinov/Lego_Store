package legostore.exception;

public class NegativePrice extends LegoStoreException {
    public NegativePrice(String message) {
        super(message);
    }
    public NegativePrice(String message, Throwable cause) {
        super(message, cause);
    }
}
