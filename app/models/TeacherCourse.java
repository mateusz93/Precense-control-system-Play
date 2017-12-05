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

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "subjectID")
    private Subject subject;

    @Column(name = "studentGroup")
    private String studentGroup;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "teacherID")
    private User teacher;

    @Column(name = "description")
    private String description;

    public static Finder<Integer, TeacherCourse> find = new Finder<>(TeacherCourse.class);

    public static TeacherCourse findOne(int id) {
        return find.query().where()
                .eq("ID", id)
                .findUnique();
    }

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

    public static void delete(int id) {
        find.deleteById(id);
    }
}
