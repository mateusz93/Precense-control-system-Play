package controllers;

import models.CourseDateView;
import models.TeacherCourseView;
import play.mvc.Result;
import views.html.precensesForTeacher;
import views.html.precensesInfo;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static play.mvc.Results.ok;

public class PrecenseController {
    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        /*Task task = new Task();
        task.id = 123L;
        task.done = true;
        task.dueDate = new Date();
        task.name = "Mateusz";
        task.save();*/
        /*
        Logic checking if logged user is student
         */
        // if Yes
        /*List<StudentPrecenseView> list = new ArrayList<>();
        list.add(new StudentPrecenseView(0,"subjectName",1, "teacherName1"));
        list.add(new StudentPrecenseView(1,"subjectName2",2, "teacherName2"));
        list.add(new StudentPrecenseView(2,"subjectName3",3, "teacherName3"));
        list.add(new StudentPrecenseView(3,"subjectName4",4, "teacherName4"));
        list.add(new StudentPrecenseView(4,"subjectName5",5, "teacherName5"));*/
        //return ok(precensesForStudent.render());
        // if No
        List<TeacherCourseView> list = new ArrayList<>();
        list.add(new TeacherCourseView(0,"subjectName",1));
        list.add(new TeacherCourseView(1,"subjectName2",2));
        list.add(new TeacherCourseView(2,"subjectName3",3));
        list.add(new TeacherCourseView(3,"subjectName4",4));
        list.add(new TeacherCourseView(4,"subjectName5",5));
        return ok(precensesForTeacher.render(list));
    }

    public Result info(Integer id) {
        //STUDENT precense/precensesInfo
        List<CourseDateView> list = new ArrayList<>();
        list.add(new CourseDateView("obecny",new Date(), new Time(0l), new Time(1000l), 0, "no", "0"));
        return ok(precensesInfo.render(list));
        //NIESTUDENT precense/checkPrecenses"
    }

    public Result check(Integer id) {
        //TODO change view template to proper
        List<CourseDateView> list = new ArrayList<>();
        list.add(new CourseDateView("obecny",new Date(), new Time(0l), new Time(1000l), 0, "no", "0"));
        return ok(precensesInfo.render(list));
    }

}
