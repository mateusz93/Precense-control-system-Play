package exceptions;

/**
 * @author Mateusz Wieczorek
 */
public class DifferentPasswordsException extends ValidationException {

    public DifferentPasswordsException(String messageCode) {
        super(messageCode);
    }
}
