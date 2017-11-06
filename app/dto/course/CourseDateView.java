package dto.course;

import dto.BaseView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;

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

}
