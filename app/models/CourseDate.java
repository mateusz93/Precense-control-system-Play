package models;

import io.ebean.Model;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

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

}
