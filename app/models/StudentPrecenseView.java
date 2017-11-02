package models;

import io.ebean.Finder;
import io.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Entity
public class StudentPrecenseView extends Model {

    @Id
    public Long courseId;

    @Constraints.Required
    public String subjectName;
    public Integer quantity;

    @Constraints.Required
    public String teacherName;

    public StudentPrecenseView(Long courseId, String subjectName, Integer quantity, String teacherName) {
        this.courseId = courseId;
        this.subjectName = subjectName;
        this.quantity = quantity;
        this.teacherName = teacherName;
    }

    public static final Finder<Long, StudentPrecenseView> find = new Finder<>(StudentPrecenseView.class);

    public static List<StudentPrecenseView> findAll() {
        List<StudentPrecenseView> list = find.query().findList();
        //MOCK DATA
        if(list.size() == 0) {
            list = new ArrayList<>();
            list.add(new StudentPrecenseView(0l,"subjectName",1, "teacherName1"));
            list.add(new StudentPrecenseView(1l,"subjectName2",2, "teacherName2"));
            list.add(new StudentPrecenseView(2l,"subjectName3",3, "teacherName3"));
            list.add(new StudentPrecenseView(3l,"subjectName4",4, "teacherName4"));
            list.add(new StudentPrecenseView(4l,"subjectName5",5, "teacherName5"));
            for (StudentPrecenseView studentPrecenseView : list) {
                studentPrecenseView.save();
            }
        }
        return list;
    }
}
