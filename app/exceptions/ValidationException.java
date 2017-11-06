package exceptions;

/**
 * @author Mateusz Wieczorek
 */
public abstract class ValidationException extends Exception {

    public ValidationException(String messageCode) {
        super(messageCode);
    }
}
