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
