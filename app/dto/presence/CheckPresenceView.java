package dto.presence;

import dto.BaseView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckPresenceView extends BaseView {

    public String ID;
    public String firstName;
    public String lastName;
    public String presenceStatus;
}
