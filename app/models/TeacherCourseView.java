package models;

public class TeacherCourseView {
    public int ID;
    public String subjectName;
    public int coursesQuantity;

    public TeacherCourseView(int ID, String subjectName, int coursesQuantity) {
        this.ID = ID;
        this.subjectName = subjectName;
        this.coursesQuantity = coursesQuantity;
    }
}
