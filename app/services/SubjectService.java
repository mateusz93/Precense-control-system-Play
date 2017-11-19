package services;

import dto.course.SubjectView;
import dto.course.FieldView;
import models.Field;
import models.Subject;
import play.data.FormFactory;
import play.mvc.Result;
import views.html.addSubject;
import views.html.subjectList;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static play.mvc.Results.ok;

public class SubjectService {

    private final FormFactory formFactory;

    @Inject
    public SubjectService(FormFactory formFactory) {
        this.formFactory = formFactory;
    }

    public Result prepareView() {
        List<Subject> subjects = Subject.findAll();
        return ok(subjectList.render(mapSubjects(subjects)));
    }

    private List<SubjectView> mapSubjects(List<Subject> subjects) {
        List<SubjectView> subjectViews = new ArrayList<>();
        for (Subject s : subjects) {
            SubjectView subjectView = new SubjectView();
            subjectView.setId(s.getId());
            subjectView.setName(s.getName());
            subjectView.setDescription(s.getDescription());
            subjectView.setField(s.getField().getName());
            subjectView.setYearOfStudy(s.getYearOfStudy());
            subjectView.setQuantity(s.getQuantity());
            subjectView.setMinQuantity(s.getMinQuantity());
            subjectViews.add(subjectView);
        }
        return subjectViews;
    }

    public Result prepareAddNewSubjectView() {
        List<Field> fields = Field.findAll();
        return ok(addSubject.render(mapFields(fields),formFactory.form(Subject.class)));
    }

    private List<FieldView> mapFields(List<Field> fields) {
        List<FieldView> fieldViews = new ArrayList<>();
        for (Field f : fields) {
            FieldView fieldView = new FieldView();
            fieldView.setId(f.getId());
            fieldView.setName(f.getName());
            fieldViews.add(fieldView);
        }
        return fieldViews;
    }
}
