package exceptions;

import lombok.Getter;

/**
 * @author Mateusz Wieczorek
 */
@Getter
public class FieldEmptyException extends ValidationException {

    private String fieldName;

    public FieldEmptyException(String messageCode) {
        super(messageCode);
    }

    public FieldEmptyException(String messageCode, String fieldName) {
        super(messageCode);
        this.fieldName = fieldName;
    }
}
