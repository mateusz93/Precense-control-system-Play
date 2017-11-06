package models;

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

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "courseDateID")
    private CourseDate courseDate;

}
