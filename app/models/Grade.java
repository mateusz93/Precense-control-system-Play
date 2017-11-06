package models;

import io.ebean.Model;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
public class Grade extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacherCourseID")
    private TeacherCourse teacherCourse;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "previousGradeID")
    private Grade previousGrade;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "studentID")
    private User user;

    @Column(name = "value")
    private int value;

    @Column(name = "isFinalGrade")
    private boolean isFinalGrade;

    @Column(name = "time")
    private Timestamp time;

}
