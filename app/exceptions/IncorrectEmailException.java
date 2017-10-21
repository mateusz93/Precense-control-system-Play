package exceptions;

/**
 * @author Mateusz Wieczorek
 */
public class IncorrectEmailException extends ValidationException {

    public IncorrectEmailException(String messageCode) {
        super(messageCode);
    }
}
