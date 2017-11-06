package dto.security;

import dto.BaseView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import play.data.validation.Constraints;

import javax.validation.constraints.NotNull;

/**
 * @author Mateusz Wieczorek
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginView extends BaseView {

    @Constraints.Required
    public String email;

    @Constraints.Required
    public String type;

    @Constraints.Required
    public String password;
}
