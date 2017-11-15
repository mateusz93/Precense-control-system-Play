package models;

import enums.Role;
import io.ebean.Finder;
import io.ebean.Model;

import lombok.Builder;
import lombok.Data;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Builder
@Data
@Entity
public class User extends Model {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "ID")
    public long id;

    @Column(name = "username")
    public String login;

    @Column(name = "firstName")
    public String firstName;

    @Column(name = "lastName")
    public String lastName;

    @Column(name = "type")
    public String type;

    @Column(name = "password")
    public String password;

    @Column(name = "lastLogin")
    public Timestamp lastLogin;

    @Column(name = "status")
    public String status;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fieldID")
    public Field field;

    @Column(name = "`group`")
    public String group;

    @Column(name = "yearOfStudy")
    public int yearOfStudy;

    @Column(name = "email")
    public String email;

    @Column(name = "phone")
    public String phone;

    @Column(name = "street")
    public String street;

    @Column(name = "city")
    public String city;

    @Transient
    public String getFullName() {
        return firstName + " " + lastName;
    }

    public static final Finder<Long, User> find = new Finder<>(User.class);

    public static User findByLogin(String login) {
        return find.query()
                .where()
                .like("username", login)
                .findOne();
    }

    public static User findByEmail(String email) {
        return find.query()
                .where()
                .like("email", email)
                .findOne();
    }

    public static User findByFirstName(String firstName) {
        return find.query()
                .where()
                .like("firstName", firstName)
                .findOne();
    }

    public static User findByLastName(String lastName) {
        return find.query()
                .where()
                .like("lastName", lastName)
                .findOne();
    }

    public static List<User> findByType(Role type) {
        return find.query()
                .where()
                .like("type", type.name())
                .findList();
    }
}
