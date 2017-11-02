package models;

import java.sql.Time;
import java.util.Date;

public class CourseDateView {
    public String status;
    public Date date;
    public Time startTime;
    public Time finishTime;
    public Integer courseDateID;
    public String isEdited;
    public String dateId;

    public CourseDateView(String status, Date date, Time startTime, Time finishTime, Integer courseDateID, String isEdited, String dateId) {
        this.status = status;
        this.date = date;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.courseDateID = courseDateID;
        this.isEdited = isEdited;
        this.dateId = dateId;
    }
}
