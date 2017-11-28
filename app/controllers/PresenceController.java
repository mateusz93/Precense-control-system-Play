package controllers;

import dto.course.CourseDateView;
import dto.course.TeacherCourseView;
import dto.presence.CheckPresenceView;
import dto.presence.CheckPresenceViewWrapper;
import dto.presence.StudentPresencesView;
import models.StudentCourse;
import models.Subject;
import models.TeacherCourse;
import models.User;
import play.mvc.Result;
import play.mvc.Security;
import services.CourseService;
import services.PresenceService;
import views.html.presence.checkPresence;
import views.html.presence.studentPresences;
import views.html.course.teacherCourseDates;
import views.html.course.teacherCoursesList;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class PresenceController extends BaseController {

    private final CourseService courseService;
    private final PresenceService presenceService;

    @Inject
    public PresenceController(CourseService courseService, PresenceService presenceService) {
        this.courseService = courseService;
        this.presenceService = presenceService;
    }

    @Security.Authenticated
    public Result index() {
        User user = User.findByLogin(request().attrs().get(Security.USERNAME));
        if("Student".equals(user.getType()))
        {
            return prepareStudentView(user);
        } else {
            return prepareTeacherView(user);
        }
    }

    private Result prepareStudentView(User user) {
        List<StudentPresencesView> coursesList = getStudentPresences(user);
        return ok(studentPresences.render(coursesList));
    }

    private Result prepareTeacherView(User user) {
        List<TeacherCourseView> coursesList = courseService.getTeacherCoursesList(user);
        return ok(teacherCoursesList.render(coursesList));
    }


    private List<StudentPresencesView> getStudentPresences(User user) {
        List<StudentPresencesView> studentPresencesViews = new ArrayList<>();
        List<StudentCourse> studentCourses = StudentCourse.findByStudent(user);
        for (StudentCourse studentCourse : studentCourses) {
            Subject subject = studentCourse.getTeacherCourse().getSubject();
            TeacherCourse teacherCourse = TeacherCourse.findBySubjectAndStudentGroup(subject, user.getGroup());
            StudentPresencesView studentPresencesView = getStudentPresencesView(subject, teacherCourse);
            studentPresencesViews.add(studentPresencesView);
        }
        return studentPresencesViews;
    }

    private StudentPresencesView getStudentPresencesView(Subject subject, TeacherCourse teacherCourse) {
        StudentPresencesView studentPresencesView = new StudentPresencesView();
        studentPresencesView.setSubjectName(subject.getName());
        studentPresencesView.setTeacherName(teacherCourse.getTeacher().getFirstName() + " " + teacherCourse.getTeacher().getLastName());
        studentPresencesView.setCourseId(teacherCourse.getId());
        studentPresencesView.setQuantity(subject.getQuantity());
        return studentPresencesView;
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
