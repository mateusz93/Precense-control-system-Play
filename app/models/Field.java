package models;

import io.ebean.Finder;
import io.ebean.Model;
import lombok.Data;
import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Field extends Model {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "name")
    private String name;

    public static Finder<Integer, Field> find = new Finder<>(Field.class);

    public static List<Field> findAll() {
        return find.query().findList();
    }
}
