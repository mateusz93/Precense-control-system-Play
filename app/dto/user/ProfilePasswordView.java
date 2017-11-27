package dto.user;

import dto.BaseView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfilePasswordView extends BaseView {

    public String password;

    public String newPassword;

    public String againNewPassword;
}
