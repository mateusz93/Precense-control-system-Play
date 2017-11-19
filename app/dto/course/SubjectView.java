package dto.course;

import lombok.Data;

@Data
public class SubjectView {

    public int id;
    public String name;
    public String description;
    public String field;
    public int yearOfStudy;
    public int quantity;
    public int minQuantity;

}
