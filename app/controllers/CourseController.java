package controllers;

import dto.course.CourseDateView;
import lombok.val;
import models.TeacherCourse;
import models.User;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Result;
import play.mvc.Security;
import services.CourseService;
import views.html.course.addCourseDate;

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
        return service.prepareViewByCourseId(teacherCourseId, user);
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
    public Result saveCourseDate() {
        User user = User.findByLogin(request().attrs().get(Security.USERNAME));
        val dto = formFactory.form(CourseDateView.class).bindFromRequest().data();
        service.addCourseDateByCourseId(dto);
        return service.prepareView(user);
    }

    @Security.Authenticated
    public Result newCourseDate(int teacherCourseId) {
        User user = User.findByLogin(request().attrs().get(Security.USERNAME));
        val dto = CourseDateView
                .builder()
                .courseDateID(teacherCourseId)
                .build();
        return ok(addCourseDate.render(dto));
    }

    @Security.Authenticated
    public Result save() {
        Form<TeacherCourse> teacherCourseForm = formFactory.form(TeacherCourse.class);
        service.saveNewCourse(teacherCourseForm.bindFromRequest().rawData());
        return index();
    }
}
