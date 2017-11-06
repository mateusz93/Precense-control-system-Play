package dto.presence;

import dto.BaseView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherPresencesView extends BaseView {

    public int id;
    public String subjectName;
    public int quantity;
}
