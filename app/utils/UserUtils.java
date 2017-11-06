package utils;

import models.User;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpSession;

/**
 * @author Mateusz Wieczorek
 */
public class UserUtils {

    public static boolean isNotLogged(String username) {
        return !StringUtils.isNotBlank(username);
    }

    public static boolean isNotLogged(User user) {
        return !(user != null && StringUtils.isNotBlank(user.getLogin()));
    }

    public static User getUserFromSession(HttpSession httpSession) {
        String username = (String) httpSession.getAttribute("username");
        if (username == null) {
            return null;
        }
        return User.findByLogin(username);
    }
}
