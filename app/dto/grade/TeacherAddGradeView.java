package dto.grade;

import dto.BaseView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Mateusz Wieczorek
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeacherAddGradeView extends BaseView {

    public String value;
    public String isFinal;
    public int courseId;
    public int studentId;
    public String studentName;

}
