package services;

import dto.course.TeacherCourseView;
import dto.presence.CheckPresenceView;
import dto.presence.CheckPresenceViewWrapper;
import dto.presence.StudentPresencesView;
import lombok.extern.slf4j.Slf4j;
import models.*;
import play.db.ebean.Transactional;
import play.mvc.Result;
import views.html.course.studentCourseDates;
import views.html.course.teacherCourseDates;
import views.html.presence.checkPresence;
import views.html.presence.studentPresences;
import views.html.presence.teacherCoursesList;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public Result preparePresencesList(int courseDateId) {
            CourseDate courseDate = CourseDate.findOne(courseDateId);
            List<StudentCourse> studentCourses = StudentCourse.findByTeacherCourse(courseDate.getTeacherCourse());
            List<CheckPresenceView> students = new ArrayList<>();

            for (StudentCourse studentCourse : studentCourses) {
                User student = User.findOne(studentCourse.getStudent().getId());
                CheckPresenceView checkPresenceView = getCheckPresenceView(courseDate, student);
                students.add(checkPresenceView);
            }
            CheckPresenceViewWrapper checkPresenceViewWrapper = new CheckPresenceViewWrapper();
            checkPresenceViewWrapper.setStudents(students);
            return ok(checkPresence.render(courseDateId, checkPresenceViewWrapper));
    }

    private CheckPresenceView getCheckPresenceView(CourseDate courseDate, User student) {
        CheckPresenceView checkPresenceView = new CheckPresenceView();
        checkPresenceView.setID(String.valueOf(student.getId()));
        checkPresenceView.setFirstName(student.getFirstName());
        checkPresenceView.setLastName(student.getLastName());

        StudentPresence studentPresence = StudentPresence.findByCourseDate(courseDate);
        if (studentPresence != null) {
            checkPresenceView.setPresenceStatus(studentPresence.getStatus());
        }
        return checkPresenceView;
    }

    public Result updateCourseDate(CheckPresenceView studentWrapper, int courseDateId) {
        updatePresences(studentWrapper, courseDateId);
        return preparePresencesList(courseDateId);
    }

    private void updatePresences(CheckPresenceView studentWrapper, int courseDateId) {
        String[] ids = studentWrapper.getID().split(",");
        String[] statuses = studentWrapper.getPresenceStatus().split(",");

        for (int i = 0; i < ids.length; ++i) {
            User user = User.findOne(Integer.valueOf(ids[i]));
            CourseDate courseDate = CourseDate.findOne(courseDateId);
            StudentPresence studentPresence = getStudentPresence(statuses, i, user, courseDate);
            studentPresence.save();
        }
    }

    private StudentPresence getStudentPresence(String[] statuses, int i, User user, CourseDate courseDate) {
        StudentPresence studentPresence = StudentPresence.findByCourseDateAndStudent(courseDate, user);
        if (studentPresence != null) {
            studentPresence.setStatus(statuses[i]);
        } else {
            studentPresence = new StudentPresence();
            studentPresence.setStatus(statuses[i]);
            studentPresence.setCourseDate(courseDate);
            studentPresence.setStudent(user);
        }
        return studentPresence;
    }

    public Result prepareCourseDates(Integer id, User user) {
        TeacherCourse teacherCourse = TeacherCourse.findOne(id);
        List<CourseDate> courseDates = CourseDate.findByTeacherCourse(teacherCourse);
        if("Student".equalsIgnoreCase(user.getType())) {
            return ok(studentCourseDates.render(courseDates.stream()
                    .map(CourseDate::getView)
                    .collect(Collectors.toList())));
        } else {
            return ok(teacherCourseDates.render(courseDates.stream()
                    .map(CourseDate::getView)
                    .collect(Collectors.toList())));
        }
    }
}
