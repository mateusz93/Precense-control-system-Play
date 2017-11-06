package dto.course;

import dto.BaseView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherCourseView extends BaseView {

    public int ID;
    public String subjectName;
    public int coursesQuantity;

}
