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
public class CourseDateView extends Model {
    public String status;
    public Date date;
    public Time startTime;
    public Time finishTime;
    @Id
    public Long courseDateID;
    public String isEdited;
    public String dateId;

    public CourseDateView(String status, Date date, Time startTime, Time finishTime, Long courseDateID, String isEdited, String dateId) {
        this.status = status;
        this.date = date;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.courseDateID = courseDateID;
        this.isEdited = isEdited;
        this.dateId = dateId;
    }

    public static final Finder<Long, CourseDateView> find = new Finder<>(CourseDateView.class);

    public static List<CourseDateView> findAll() {
        List<CourseDateView> list = find.query().findList();
        //MOCK DATA
        if(list.size() == 0) {
            list = new ArrayList<>();
            list.add(new CourseDateView("Obecny",new Date(), new Time(0l), new Time(1000l), 0l, "no", "0"));
            for (CourseDateView courseDateView : list) {
                courseDateView.save();
            }
        }
        return list;
    }
}
