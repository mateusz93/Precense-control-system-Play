package exceptions;

/**
 * @author Mateusz Wieczorek
 */
public class IncorrectPasswordException extends ValidationException {

    public IncorrectPasswordException(String messageCode) {
        super(messageCode);
    }
}
