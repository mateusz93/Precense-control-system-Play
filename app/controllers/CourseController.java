package controllers;

import models.CourseDate;
import models.TeacherCourse;
import models.User;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Result;
import play.mvc.Security;
import services.CourseService;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CourseController extends BaseController {

    private final CourseService service;
    private final FormFactory formFactory;

    @Inject
    public CourseController(CourseService service, FormFactory formFactory) {
        this.service = service;
        this.formFactory = formFactory;
    }

    @Security.Authenticated
    public Result index() {
        User user = User.findByLogin(request().attrs().get(Security.USERNAME));
        return service.prepareView(user);
    }

    @Security.Authenticated
    public Result info(Integer teacherCourseId) {
        User user = User.findByLogin(request().attrs().get(Security.USERNAME));
        return play.mvc.Results.TODO;//service.prepareViewByCourseId(teacherCourseId, user);
    }

    @Security.Authenticated
    public Result newCourse() {
        User user = User.findByLogin(request().attrs().get(Security.USERNAME));
        return service.prepareNewCourseView();
    }

    @Security.Authenticated
    public Result deleteCourse(Integer id) {
        TeacherCourse.delete(id);
        return index();
    }

    @Security.Authenticated
    public Result save() {
        Form<TeacherCourse> teacherCourseForm = formFactory.form(TeacherCourse.class);
        service.saveNewCourse(teacherCourseForm.bindFromRequest().rawData());
        return index();
    }
}
