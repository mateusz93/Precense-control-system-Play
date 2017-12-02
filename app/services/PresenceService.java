package services;

import dto.course.TeacherCourseView;
import dto.presence.StudentPresencesView;
import lombok.extern.slf4j.Slf4j;
import models.StudentCourse;
import models.Subject;
import models.TeacherCourse;
import models.User;
import play.db.ebean.Transactional;
import play.mvc.Result;
import views.html.course.teacherCoursesList;
import views.html.presence.studentPresences;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

import static play.mvc.Results.ok;

@Slf4j
@Transactional
@Singleton
public class PresenceService {

    private final CourseService courseService;

    @Inject
    public PresenceService(CourseService courseService) {
        this.courseService = courseService;
    }

    public Result prepareStudentView(User user) {
        List<StudentPresencesView> coursesList = getStudentPresences(user);
        return ok(studentPresences.render(coursesList));
    }

    public Result prepareTeacherView(User user) {
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
}
