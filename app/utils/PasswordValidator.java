package utils;

/**
 * @author Mateusz Wieczorek
 */
public class PasswordValidator {

    private static final String PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,20}";

    public static boolean validate(String password) {
        return password.matches(PATTERN);
    }

}
