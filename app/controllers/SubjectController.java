package controllers;

import models.Subject;
import play.data.Form;
import play.mvc.Result;
import play.mvc.Security;
import services.SubjectService;
import play.data.FormFactory;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SubjectController extends BaseController {

    private final FormFactory formFactory;
    private final SubjectService subjectService;

    @Inject
    public SubjectController(FormFactory formFactory, SubjectService subjectService) {
        this.formFactory = formFactory;
        this.subjectService = subjectService;
    }

    @Security.Authenticated
    public Result course() {
        return subjectService.prepareView();
    }

    public Result newSubject() {
        return subjectService.prepareAddNewSubjectView();
    }

    public Result delete(Integer id) {
        Subject.delete(id);
        return subjectService.prepareView();
    }

    public Result save() {
        Form<Subject> subjectForm = formFactory.form(Subject.class);
        Subject subject = subjectForm.bindFromRequest().get();
        subject.save();
        return subjectService.prepareView();
    }
}
