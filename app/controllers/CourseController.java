package controllers;

import models.User;
import play.mvc.Result;
import play.mvc.Security;
import services.CourseService;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CourseController extends BaseController {

    private final CourseService service;

    @Inject
    public CourseController(CourseService service) {
        this.service = service;
    }

    @Security.Authenticated
    public Result index() {
        User user = User.findByLogin(request().attrs().get(Security.USERNAME));
        return service.prepareView(user);
    }

    public Result info(Integer teacherCourseId) {
        User user = User.findByLogin(request().attrs().get(Security.USERNAME));
        return play.mvc.Results.TODO;//service.prepareViewByCourseId(teacherCourseId, user);
    }

    @Security.Authenticated
    public Result newCourse() {
        User user = User.findByLogin(request().attrs().get(Security.USERNAME));
        return service.prepareNewCourseView();
    }

    public Result deleteCourse(Integer id) {
        return play.mvc.Results.TODO;
    }

    public Result addCourseDate(Integer id) {
        return play.mvc.Results.TODO;
    }

    public Result save() {
        return play.mvc.Results.TODO;
    }
}
