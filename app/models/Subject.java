package models;

import io.ebean.Model;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Subject extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fieldID")
    private Field field;

    @Column(name = "yearOfStudy")
    private int yearOfStudy;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "minQuantity")
    private int minQuantity;

}
