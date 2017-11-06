package dto.presence;

import dto.BaseView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentPresenceView extends BaseView {

    public int courseId;
    public String subjectName;
    public int quantity;
    public String teacherName;

}
