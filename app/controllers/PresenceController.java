package controllers;

import dto.course.CourseDateView;
import dto.course.TeacherCourseView;
import dto.presence.CheckPresenceView;
import dto.presence.CheckPresenceViewWrapper;
import play.mvc.Result;
import views.html.checkPresence;
import views.html.teacherCourseDates;
import views.html.teacherCoursesList;

import javax.inject.Singleton;
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
        /*
        Logic checking if logged user is student
         */
        // if Yes
        //getting from database and convert from MODEL TO DTO
        //return ok(presencesForStudent.render(StudentPresenceView.findAll());
        // if No
        //getting from database and convert from MODEL TO DTO
        List<TeacherCourseView> coursesList = TeacherCourseView.findAll();
        return ok(teacherCoursesList.render(coursesList));
    }

    public Result courseDates(Integer id) {
        //STUDENT presence/presencesInfo
        //return ok(presencesInfo.render(CourseDateView.findAll()));
        //NIESTUDENT presence/checkPresences")
        return ok(teacherCourseDates.render(CourseDateView.findAll()));
    }

    public Result check(Integer id) {
        //TODO change view template to proper
        CheckPresenceViewWrapper checkPresenceViewWrapper = new CheckPresenceViewWrapper();
        checkPresenceViewWrapper.setStudents(CheckPresenceView.findAll());
        return ok(checkPresence.render(0, checkPresenceViewWrapper));
    }

    public Result cancel(Integer id) {
        //TODO change view template to proper
        CheckPresenceViewWrapper checkPresenceViewWrapper = new CheckPresenceViewWrapper();
        checkPresenceViewWrapper.setStudents(CheckPresenceView.findAll());
        return ok(checkPresence.render(0, checkPresenceViewWrapper));
    }

    public Result update(Integer id) {
        //TODO change view template to proper
        CheckPresenceViewWrapper checkPresenceViewWrapper = new CheckPresenceViewWrapper();
        checkPresenceViewWrapper.setStudents(CheckPresenceView.findAll());
        return ok(checkPresence.render(0, checkPresenceViewWrapper));
    }

}
