package models;

import io.ebean.Finder;
import io.ebean.Model;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class TeacherCourse extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subjectID")
    private Subject subject;

    @Column(name = "studentGroup")
    private String studentGroup;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacherID")
    private User teacher;

    @Column(name = "description")
    private String description;

    public static Finder<Long,TeacherCourse> find = new Finder<Long, TeacherCourse>(TeacherCourse.class);

    public static TeacherCourse findBySubjectAndStudentGroup(Subject subject, String group) {
        return find.query().where()
            .eq("subject", subject)
            .eq("studentGroup", group)
            .findUnique();
    }

    public static List<TeacherCourse> findByTeacher(User user) {
        return find.query().where()
            .eq("teacher", user)
            .findList();
    }

    public static List<TeacherCourse> findAll() {
        return find.query().findList();
    }
}
