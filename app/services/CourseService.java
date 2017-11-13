package services;

import dto.course.TeacherCourseView;
import models.TeacherCourse;
import models.User;

import java.util.ArrayList;
import java.util.List;

public class CourseService {

    public List<TeacherCourseView> getTeacherCoursesList(User user) {
        List<TeacherCourse> courses = TeacherCourse.findByTeacher(user);
        return mapTeacherCourses(courses, user);
    }

    private List<TeacherCourseView> mapTeacherCourses(List<TeacherCourse> courses, User user) {
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
}