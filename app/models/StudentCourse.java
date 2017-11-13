package models;

import io.ebean.Finder;
import io.ebean.Model;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class StudentCourse extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacherCourseID")
    private TeacherCourse teacherCourse;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "studentID")
    private User student;

    public static Finder<Long,StudentCourse> find = new Finder<Long, StudentCourse>(StudentCourse.class);

    public static List<StudentCourse> findByStudent(User student) {
        return find.query().where()
                .eq("student", student)
                .findList();
    }
}
