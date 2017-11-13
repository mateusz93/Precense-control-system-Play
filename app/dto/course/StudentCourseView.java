package dto.course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Marcin Korycki, 13.11.17.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentCourseView {

    private int courseId;
    private String name;
    private String description;
    private int coursesQuantity;
    private String teacherName;

}
