package controllers;

import dto.grade.TeacherAddGradeView;
import enums.UserType;
import lombok.val;
import models.User;
import play.data.FormFactory;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import services.GradeService;
import services.PdfService;
import views.html.grade.teacherCoursesList;
import views.html.grade.studentGrades;
import views.html.grade.studentGradesDetails;
import views.html.grade.teacherAddGrade;
import views.html.grade.teacherGrades;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.time.LocalDate;

@Singleton
public class GradeController extends BaseController {

    private final GradeService service;
    private final PdfService pdfService;
    private final FormFactory formFactory;

    @Inject
    public GradeController(GradeService service, PdfService pdfService, FormFactory formFactory) {
        this.service = service;
        this.pdfService = pdfService;
        this.formFactory = formFactory;
    }

    @Security.Authenticated
    public Result course() {
        val username = Http.Context.current().session().get(USERNAME);
        val user = User.findByLogin(username);
        if (UserType.STUDENT.name().equalsIgnoreCase(user.getType())) {
            val dto = service.prepareStudentGrades(user);
            return ok(studentGrades.render(dto));
        } else {
            val dto = service.prepareTeacherView(user);
            return ok(teacherCoursesList.render(dto));
        }
    }
    @Security.Authenticated
    public Result downloadPdf() {
        val username = Http.Context.current().session().get(USERNAME);
        val user = User.findByLogin(username);
        pdfService.generateGradesPdf(user);
        return ok(new File("grades.pdf"), "grades_" + LocalDate.now() + ".pdf");
    }

    @Security.Authenticated
    public Result gradesInfo(int courseId) {
        val dto = service.prepareStudentGradesDetails(courseId);
        return ok(studentGradesDetails.render(dto));
    }

    @Security.Authenticated
    public Result grades(int courseId) {
        val username = Http.Context.current().session().get(USERNAME);
        val user = User.findByLogin(username);
        if (UserType.STUDENT.name().equalsIgnoreCase(user.getType())) {
            val dto = service.prepareStudentGradesDetails(courseId);
            return ok(studentGradesDetails.render(dto));
        } else {
            val dto = service.prepareTeacherGrades(courseId);
            return ok(teacherGrades.render(dto));
        }
    }

    @Security.Authenticated
    public Result newGrade(int courseId, int studentId) {
        val username = Http.Context.current().session().get(USERNAME);
        val user = User.findByLogin(username);
        if (UserType.STUDENT.name().equalsIgnoreCase(user.getType())) {
            val dto = service.prepareStudentGradesDetails(courseId);
            return ok(studentGradesDetails.render(dto));
        } else {
            val dto = service.newCourseByCourseIdAndStudentId(courseId, studentId);
            return ok(teacherAddGrade.render(dto));
        }
            }

    @Security.Authenticated
    public Result saveGrade(int courseId, int studentId) {
        val view = formFactory.form(TeacherAddGradeView.class).bindFromRequest().get();
        service.updateCourseGrades(courseId, studentId, view);
        val dto = service.prepareTeacherGrades(courseId);
        return ok(teacherGrades.render(dto));
    }
}
