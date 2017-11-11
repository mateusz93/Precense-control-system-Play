package dto.course;

import dto.BaseView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherCourseView extends BaseView {

    public int ID;
    public String subjectName;
    public int coursesQuantity;

    //mock data - to remove when data will be fetched from database and rewrite to dto
    public static List<TeacherCourseView> findAll() {
        List<TeacherCourseView> list = new ArrayList<>();
        list.add(new TeacherCourseView(0, "subjectName", 1));
        list.add(new TeacherCourseView(1, "subjectName2", 2));
        list.add(new TeacherCourseView(2, "subjectName3", 3));
        list.add(new TeacherCourseView(3, "subjectName4", 4));
        list.add(new TeacherCourseView(4, "subjectName5", 5));
        return list;
    }
}
