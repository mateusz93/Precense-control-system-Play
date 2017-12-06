package models;

import io.ebean.Finder;
import io.ebean.Model;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Builder
@Entity
@Data
public class StudentCourse extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacherCourseID")
    private TeacherCourse teacherCourse;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "studentID")
    private User student;

    public static Finder<Long,StudentCourse> find = new Finder<>(StudentCourse.class);

    public static List<StudentCourse> findOne(int id) {
        return find.query().where()
                .eq("ID", id)
                .findList();
    }

    public static List<StudentCourse> findByStudent(User student) {
        return find.query().where()
                .eq("student", student)
                .findList();
    }

    public static List<StudentCourse> findByTeacherCourse(TeacherCourse teacherCourse) {
        return find.query().where()
                .eq("teacherCourse", teacherCourse)
                .findList();
    }
}
