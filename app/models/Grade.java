package models;

import io.ebean.Finder;
import io.ebean.Model;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
public class Grade extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacherCourseID")
    private TeacherCourse teacherCourse;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "studentID")
    private User user;

    @Column(name = "value")
    public int value;

    @Column(name = "isFinalGrade")
    private boolean isFinalGrade;

    @Column(name = "time")
    private Timestamp time;

    public static Finder<Integer, Grade> find = new Finder<>(Grade.class);

    public static List<Grade> findByStudent(User user) {
        return find.query()
                .where()
                .eq("studentID", user.getId())
                .findList();

    }

    public static List<Grade> findByTeacherCourse(TeacherCourse teacherCourse) {
        return find.query()
                .where()
                .eq("teacherCourseID", teacherCourse.getId())
                .findList();

    }
}
