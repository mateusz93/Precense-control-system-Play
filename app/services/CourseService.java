package services;

import dto.course.StudentCourseView;
import dto.course.TeacherCourseView;
import enums.Role;
import enums.UserType;
import models.StudentCourse;
import models.Subject;
import models.TeacherCourse;
import models.User;
import play.mvc.Result;
import views.html.course.addCourseDate;
import views.html.course.adminCoursesList;
import views.html.course.studentCoursesList;
import views.html.course.teacherCoursesList;

import java.util.ArrayList;
import java.util.List;

import static play.mvc.Results.ok;

public class CourseService {

    public List<TeacherCourseView> getTeacherCoursesList(User user) {
        List<TeacherCourse> courses = TeacherCourse.findByTeacher(user);
        return mapTeacherCourses(courses);
    }

    private List<TeacherCourseView> mapTeacherCourses(List<TeacherCourse> courses) {
        List<TeacherCourseView> courseViews = new ArrayList<>();
        for (TeacherCourse c : courses) {
            TeacherCourseView courseView = new TeacherCourseView();
            courseView.setCoursesQuantity(c.getSubject().getQuantity());
            courseView.setID(c.getId());
            courseView.setSubjectName(c.getSubject().getName());
            courseViews.add(courseView);
        }
        return courseViews;
    }

    public Result prepareView(User user) {
        if (UserType.STUDENT.name().equals(user.getType())) {
            return prepareStudentView(user);
        } else if (UserType.TEACHER.name().equals(user.getType())) {
            return prepareTeacherView(user);
        }
        else /*if (UserType.ADMIN.name().equals(user.getType()))*/ {
            return prepareAdminView();
        }

    }

    private Result prepareAdminView() {
        List<TeacherCourse> coursesList = TeacherCourse.findAll();
        return ok(adminCoursesList.render(mapTeacherCourses(coursesList)));
    }

    private Result prepareTeacherView(User user) {
        List<TeacherCourseView> coursesList = getTeacherCoursesList(user);
        return ok(teacherCoursesList.render(coursesList));
    }

    private Result prepareStudentView(User user) {
        List<StudentCourseView> coursesList = getStudentCoursesList(user);
        return ok(studentCoursesList.render(coursesList));
    }

    private List<StudentCourseView> getStudentCoursesList(User user) {
        List<StudentCourse> courses = StudentCourse.findByStudent(user);
        return mapStudentCourses(courses);
    }

    private List<StudentCourseView> mapStudentCourses(List<StudentCourse> courses) {
        List<StudentCourseView> courseViews = new ArrayList<>();
        for (StudentCourse c : courses) {
            StudentCourseView courseView = new StudentCourseView();
            courseView.setCoursesQuantity(c.getTeacherCourse().getSubject().getQuantity());
            courseView.setDescription(c.getTeacherCourse().getSubject().getDescription());
            courseView.setName(c.getTeacherCourse().getSubject().getName());
            courseView.setCourseId(c.getTeacherCourse().getId());
            courseView.setTeacherName(c.getTeacherCourse().getTeacher().getFirstName() + " " +
                    c.getTeacherCourse().getTeacher().getLastName());
            courseViews.add(courseView);
        }
        return courseViews;
    }

    public Result prepareNewCourseView() {
        List<Subject> subjects = Subject.findAll();
        List<User> teachers = User.findByType(Role.TEACHER);
        return ok(addCourseDate.render(subjects,teachers));
    }
}