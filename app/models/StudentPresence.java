package models;

import io.ebean.Finder;
import io.ebean.Model;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class StudentPresence extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;

    @Column(name = "status")
    private String status;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "studentID")
    private User student;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "courseDateID")
    private CourseDate courseDate;

    public static Finder<Integer, StudentPresence> find = new Finder<>(StudentPresence.class);

    public static StudentPresence findByCourseDate(CourseDate courseDate) {
        return find.query().where()
                .eq("courseDate", courseDate)
                .findUnique();
    }

    public static StudentPresence findByCourseDateAndStudent(CourseDate courseDate, User user) {
        return find.query().where()
                .eq("courseDate", courseDate)
                .eq("student",user)
                .findUnique();
    }
}
