package dto.course;

import dto.BaseView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import models.Subject;
import models.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherCourseAdminView extends BaseView {
    public int id;
    public Subject subject;
    public String studentGroup;
    public User teacher;
    public String description;
}
