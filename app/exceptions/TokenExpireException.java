package exceptions;

/**
 * @author Mateusz Wieczorek
 */
public class TokenExpireException extends ValidationException {

    public TokenExpireException(String messageCode) {
        super(messageCode);
    }
}
