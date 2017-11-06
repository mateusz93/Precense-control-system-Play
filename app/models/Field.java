package models;

import io.ebean.Model;
import lombok.Data;
import javax.persistence.*;

@Data
@Entity
public class Field extends Model {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "name")
    private String name;
}
