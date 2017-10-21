package exceptions;

/**
 * @author Mateusz Wieczorek
 */
public class UploadFileException extends ValidationException {

    public UploadFileException(String messageCode) {
        super(messageCode);
    }
}
