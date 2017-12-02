package dto.grade;

import dto.BaseView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import models.Grade;

/**
 * @author Mateusz Wieczorek on 28.11.2017.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class StudentGradeDetailsView extends BaseView {

    public String time;
    public int value;
    public boolean finalGrade;
}
