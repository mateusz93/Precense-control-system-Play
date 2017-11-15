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

    public int courseId;
    public String name;
    public String description;
    public int coursesQuantity;
    public String teacherName;

}
