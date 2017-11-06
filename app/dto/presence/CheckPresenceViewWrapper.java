package dto.presence;

import dto.BaseView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckPresenceViewWrapper extends BaseView {

    public List<CheckPresenceView> students;
}
