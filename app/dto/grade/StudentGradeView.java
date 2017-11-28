package dto.grade;

import dto.BaseView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Mateusz Wieczorek
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentGradeView extends BaseView {

    public int courseId;
    public String name;
    public String teacherName;
}
