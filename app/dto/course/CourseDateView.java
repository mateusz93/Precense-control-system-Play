package dto.course;

import dto.BaseView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDateView extends BaseView {

    public int id;
    public String status;
    public Date date;
    public Time startTime;
    public Time finishTime;
    public Integer courseDateID;
    public String isEdited;
}
