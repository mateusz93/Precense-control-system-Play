package exceptions;

/**
 * @author Mateusz Wieczorek
 */
public class EmailExistsException extends ValidationException {

    public EmailExistsException(String messageCode) {
        super(messageCode);
    }
}
