package legostore.exception;

public class WrongInput extends LegoStoreException {
    public WrongInput(String message) {
        super(message);
    }
    public WrongInput(String message, Throwable cause) {
        super(message, cause);
    }
}
