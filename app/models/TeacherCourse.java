package models;

import io.ebean.Model;
import lombok.Data;

import javax.persistence.*;

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

}
