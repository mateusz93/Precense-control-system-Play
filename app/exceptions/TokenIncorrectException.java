package exceptions;

/**
 * @author Mateusz Wieczorek
 */
public class TokenIncorrectException extends ValidationException {

    public TokenIncorrectException(String messageCode) {
        super(messageCode);
    }
}
