package controllers;

import dto.course.CourseDateView;
import dto.course.TeacherCourseView;
import play.mvc.Result;
import views.html.checkPresences;
import views.html.presencesForTeacher;
import views.html.presencesInfo;

import javax.inject.Singleton;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Singleton
public class PresenceController extends BaseController {
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
        /*List<StudentPresenceView> list = new ArrayList<>();
        list.add(new StudentPresenceView(0,"subjectName",1, "teacherName1"));
        list.add(new StudentPresenceView(1,"subjectName2",2, "teacherName2"));
        list.add(new StudentPresenceView(2,"subjectName3",3, "teacherName3"));
        list.add(new StudentPresenceView(3,"subjectName4",4, "teacherName4"));
        list.add(new StudentPresenceView(4,"subjectName5",5, "teacherName5"));*/
        //return ok(presencesForStudent.render());
        // if No
        List<TeacherCourseView> list = new ArrayList<>();
        list.add(new TeacherCourseView(0,"subjectName",1));
        list.add(new TeacherCourseView(1,"subjectName2",2));
        list.add(new TeacherCourseView(2,"subjectName3",3));
        list.add(new TeacherCourseView(3,"subjectName4",4));
        list.add(new TeacherCourseView(4,"subjectName5",5));
        return ok(presencesForTeacher.render(list));
    }

    public Result info(Integer id) {
        //STUDENT precense/presencesInfo
        List<CourseDateView> list = new ArrayList<>();
        list.add(new CourseDateView("obecny",new Date(), new Time(0l), new Time(1000l), 0, "no", "0"));
        return ok(presencesInfo.render(list));
        //NIESTUDENT precense/checkPresences"
    }

    public Result check(Integer id) {
        //TODO change view template to proper
        List<CourseDateView> list = new ArrayList<>();
        list.add(new CourseDateView("obecny",new Date(), new Time(0l), new Time(1000l), 0, "no", "0"));
        return ok(presencesInfo.render(list));
    }

    public Result update(Long id) {

        //TODO change view template to proper
        List<CourseDateView> list = new ArrayList<>();
        list.add(new CourseDateView("obecny",new Date(), new Time(0l), new Time(1000l), 0, "no", "0"));
        return ok(checkPresences.render(list));
    }
}
