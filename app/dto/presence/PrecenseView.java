package dto.presence;

import dto.BaseView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import models.TeacherCourse;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrecenseView extends BaseView {

    public int id;
    public Date startTime;
    public Date finishTime;
    public Date date;
    public TeacherCourse courseID;
}
