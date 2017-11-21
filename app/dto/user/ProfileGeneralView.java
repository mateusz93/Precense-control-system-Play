package dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import play.data.validation.Constraints;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileGeneralView extends ProfilePhotoView {

    @Constraints.Required
    public String firstName;

    @Constraints.Required
    public String lastName;

    @Constraints.Required
    public String email;

    @Constraints.Required
    public long ID;

    public String group;

    @Constraints.Required
    public String type;

    public String phone;

    public String city;

    public String street;

}