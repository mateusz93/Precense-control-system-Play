package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class CheckPresenceView extends Model {
    @Id
    public String ID;
    public String firstName;
    public String lastName;
    public String precenseStatus;

    public CheckPresenceView(String ID, String firstName, String lastName, String precenseStatus) {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.precenseStatus = precenseStatus;
    }

    public static final Finder<Long, CheckPresenceView> find = new Finder<>(CheckPresenceView.class);

    public static List<CheckPresenceView> findAll() {
        List<CheckPresenceView> list = find.query().findList();
        //MOCK DATA
        if(list.size() == 0) {
            list = new ArrayList<>();
            list.add(new CheckPresenceView("0", "Adam", "Kowalski", "Obecny"));
            for (CheckPresenceView checkPresenceView : list) {
                checkPresenceView.save();
            }
        }
        return list;
    }
}
