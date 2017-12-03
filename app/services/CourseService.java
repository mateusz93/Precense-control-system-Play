package services;

import dto.course.StudentCourseView;
import dto.course.TeacherCourseAdminView;
import dto.course.TeacherCourseView;
import enums.Role;
import enums.UserType;
import models.*;
import play.mvc.Result;
import views.html.course.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    private List<TeacherCourseAdminView> mapTeacherCoursesForAdmin(List<TeacherCourse> courses) {
        List<TeacherCourseAdminView> courseViews = new ArrayList<>();
        for (TeacherCourse c : courses) {
            TeacherCourseAdminView courseView = new TeacherCourseAdminView();
            courseView.setId(c.getId());
            courseView.setSubject(c.getSubject());
            courseView.setStudentGroup(c.getStudentGroup());
            courseView.setTeacher(c.getTeacher());
            courseView.setDescription(c.getDescription());
            courseViews.add(courseView);
        }
        return courseViews;
    }

    public Result prepareView(User user) {
        if (UserType.STUDENT.name().equalsIgnoreCase(user.getType())) {
            return prepareStudentView(user);
        } else if (UserType.TEACHER.name().equalsIgnoreCase(user.getType())) {
            return prepareTeacherView(user);
        }
        else {
            return prepareAdminView();
        }
    }

    private Result prepareAdminView() {
        List<TeacherCourse> coursesList = TeacherCourse.findAll();
        return ok(adminCoursesList.render(mapTeacherCoursesForAdmin(coursesList)));
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

    public void saveNewCourse(Map<String,String> values) {
        User teacher = User.findByLogin(values.get("teacherLogin"));
        Subject subject = Subject.findByName(values.get("subjectName"));
        TeacherCourse teacherCourse = new TeacherCourse();
        teacherCourse.setDescription(values.get("description"));
        teacherCourse.setStudentGroup(values.get("studentGroup"));
        teacherCourse.setSubject(subject);
        teacherCourse.setTeacher(teacher);
        teacherCourse.save();
    }

    public Result prepareViewByCourseId(Integer teacherCourseId, User user) {
        TeacherCourse teacherCourse = TeacherCourse.findOne(teacherCourseId);
        List<CourseDate> courseDates = CourseDate.findByTeacherCourse(teacherCourse);
        if("Student".equalsIgnoreCase(user.getType())) {
            return ok(studentCourseDates.render(courseDates.stream().map(CourseDate::getView).collect(Collectors.toList())));
        } else {
            return ok(teacherCourseDates.render(courseDates.stream().map(CourseDate::getView).collect(Collectors.toList())));
        }
    }
}