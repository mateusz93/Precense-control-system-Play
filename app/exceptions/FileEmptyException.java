package exceptions;

/**
 * @author Mateusz Wieczorek
 */
public class FileEmptyException extends ValidationException {

    public FileEmptyException(String messageCode) {
        super(messageCode);
    }
}
