package exceptions;

/**
 * @author Mateusz Wieczorek
 */
public class IncorrectCaptchaException extends ValidationException {

    public IncorrectCaptchaException(String messageCode) {
        super(messageCode);
    }
}
