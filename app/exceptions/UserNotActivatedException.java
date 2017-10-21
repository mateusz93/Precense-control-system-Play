package exceptions;

/**
 * @author Mateusz Wieczorek
 */
public class UserNotActivatedException extends ValidationException {

    public UserNotActivatedException(String messageCode){
        super(messageCode);
    }
}
