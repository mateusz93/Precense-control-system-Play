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
    private long id;

    @Column(name = "loginSubmit")
    private String login;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "type")
    private String type;

    @Column(name = "password")
    private String password;

    @Column(name = "lastLogin")
    private Timestamp lastLogin;

    @Column(name = "status")
    private String status;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fieldID")
    private Field field;

    @Column(name = "`group`")
    private String group;

    @Column(name = "yearOfStudy")
    private int yearOfStudy;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "street")
    private String street;

    @Column(name = "city")
    private String city;

    @Transient
    public String getFullName() {
        return firstName + " " + lastName;
    }

    public static final Finder<Long, User> find = new Finder<>(User.class);

    public static User findByLogin(String login) {
        return find.query()
                .where()
                .like("loginSubmit", login)
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
