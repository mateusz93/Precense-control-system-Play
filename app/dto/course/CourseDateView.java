package dto.course;

import dto.BaseView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDateView extends BaseView {

    public String status;
    public Date date;
    public Time startTime;
    public Time finishTime;
    public Integer courseDateID;
    public String isEdited;
    public String dateId;

    //mock data
    public static List<CourseDateView> findAll() {
        List<CourseDateView> list = new ArrayList<>();
        list.add(new CourseDateView("status",new Date(), new Time(new Date().getTime()), new Time(new Date().getTime()), 1, "no","0"));
        return list;
    }
}
