package models;

import io.ebean.Finder;
import io.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TeacherCourseView extends Model {

    @Id
    public Long ID;

    @Constraints.Required
    public String subjectName;

    public Integer coursesQuantity;

    public TeacherCourseView(Long ID, String subjectName, Integer coursesQuantity) {
        this.ID = ID;
        this.subjectName = subjectName;
        this.coursesQuantity = coursesQuantity;
    }

    public static final Finder<Long, TeacherCourseView> find = new Finder<>(TeacherCourseView.class);

    public static List<TeacherCourseView> findAll() {
        List<TeacherCourseView> list = find.query().findList();
        //MOCK DATA
        if(list.size() == 0) {
            list = new ArrayList<>();
            list.add(new TeacherCourseView(0l, "subjectName", 1));
            list.add(new TeacherCourseView(1l, "subjectName2", 2));
            list.add(new TeacherCourseView(2l, "subjectName3", 3));
            list.add(new TeacherCourseView(3l, "subjectName4", 4));
            list.add(new TeacherCourseView(4l, "subjectName5", 5));
            for (TeacherCourseView teacherCourseView : list) {
                teacherCourseView.save();
            }
        }
        return list;
    }
}
