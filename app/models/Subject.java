package models;

import io.ebean.Finder;
import io.ebean.Model;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Subject extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    public int id;

    @Column(name = "name")
    public String name;

    @Column(name = "description")
    public String description;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fieldID")
    public Field field;

    @Column(name = "yearOfStudy")
    public int yearOfStudy;

    @Column(name = "quantity")
    public int quantity;

    @Column(name = "minQuantity")
    public int minQuantity;

    public static Finder<Long,Subject> find = new Finder<Long, Subject>(Subject.class);

    public static List<Subject> findAll() {
        return find.query().findList();
    }
}
