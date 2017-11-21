package dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import play.data.validation.Constraints;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfilePasswordView extends ProfilePhotoView {

    public String password;

    public String newPassword;

    public String againNewPassword;
}
