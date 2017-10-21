package exceptions;

/**
 * @author Mateusz Wieczorek
 */
abstract class ValidationException extends Exception {

    public ValidationException(String messageCode) {
        super(messageCode);
    }
}
