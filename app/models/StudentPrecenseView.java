package models;

public class StudentPrecenseView {
    public int courseId;
    public String subjectName;
    public int quantity;
    public String teacherName;

    public StudentPrecenseView(int courseId, String subjectName, int quantity, String teacherName) {
        this.courseId = courseId;
        this.subjectName = subjectName;
        this.quantity = quantity;
        this.teacherName = teacherName;
    }
}
