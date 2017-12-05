package dto.security;

import dto.BaseView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import play.data.validation.Constraints;

/**
 * @author Mateusz Wieczorek
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterView extends BaseView {

    @Constraints.Required
    public String firstName;

    @Constraints.Required
    public String lastName;

    @Constraints.Required
    public String email;

    @Constraints.Required
    public String password;

    @Constraints.Required
    public String confirmPassword;

    @Constraints.Required
    public String type;

    @Constraints.Required
    public String groupName;
}
