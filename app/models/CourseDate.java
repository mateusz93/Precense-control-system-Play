package models;

import dto.course.CourseDateView;
import io.ebean.Finder;
import io.ebean.Model;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Entity
@Data
public class CourseDate extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacherCourseID")
    private TeacherCourse teacherCourse;

    @Column(name = "startTime")
    private Time startTime;

    @Column(name = "finishTime")
    private Time finishTime;

    @Column(name = "date")
    private Date date;

    public static Finder<Integer, CourseDate> find = new Finder<>(CourseDate.class);

    public static void delete(int id) {
        find.deleteById(id);
    }

    public static CourseDate findOne(int courseDateId) {
        return find.query().where()
                .eq("ID", courseDateId)
                .findUnique();
    }

    public static List<CourseDate> findByTeacherCourse(TeacherCourse teacherCourse) {
        return find.query().where()
                .eq("teacherCourse", teacherCourse)
                .findList();
    }

    public CourseDateView getView() {
        CourseDateView courseDateView = new CourseDateView();
        courseDateView.setDate(date);
        courseDateView.setStartTime(startTime);
        courseDateView.setFinishTime(finishTime);
        return courseDateView;
    }
}
