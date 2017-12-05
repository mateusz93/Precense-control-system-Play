package models;

import io.ebean.Finder;
import io.ebean.Model;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class StudentGroup extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public int id;

    @Column(name = "name")
    public String name;

    public static final Finder<Long, StudentGroup> find = new Finder<>(StudentGroup.class);

    public static StudentGroup findOne(int id) {
        return find.query()
                .where()
                .eq("ID", id)
                .findOne();
    }

    public static StudentGroup findByName(String name) {
        return find.query()
                .where()
                .ilike("name", name)
                .findOne();
    }

    public static List<StudentGroup> findAll() {
        return find.query()
                .findList();
    }
}
