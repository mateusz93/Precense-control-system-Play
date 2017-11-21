package dto.user;

import dto.BaseView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class ProfilePhotoView extends BaseView {

    public String photoPath;
}
