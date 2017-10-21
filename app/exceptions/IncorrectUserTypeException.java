package exceptions;

/**
 * @author Mateusz Wieczorek
 */
public class IncorrectUserTypeException extends ValidationException {

    public IncorrectUserTypeException(String messageCode) {
        super(messageCode);
    }

}
