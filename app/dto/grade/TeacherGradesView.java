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
public class TeacherGradesView extends BaseView {

    public int studentId;
    public String firstName;
    public String lastName;
    public String grades;
    public String finalGrade;
    public int courseId;

}
