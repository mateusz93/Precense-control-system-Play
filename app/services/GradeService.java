package services;

import com.google.common.collect.Lists;
import dto.course.TeacherCourseView;
import dto.grade.StudentGradeDetailsView;
import dto.grade.StudentGradeView;
import dto.grade.TeacherAddGradeView;
import dto.grade.TeacherGradesView;
import lombok.extern.slf4j.Slf4j;
import models.Grade;
import models.StudentCourse;
import models.TeacherCourse;
import models.User;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import play.db.ebean.Transactional;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@Singleton
public class GradeService {

    private final CourseService courseService;

    @Inject
    public GradeService(CourseService courseService) {
        this.courseService = courseService;
    }

    public List<StudentGradeView> prepareStudentGrades(User user) {
        List<StudentGradeView> studentGradeViews = Lists.newArrayList();
        List<StudentCourse> studentCourses = StudentCourse.findByStudent(user);
        for (StudentCourse studentCourse : studentCourses) {
            StudentGradeView studentGradeView = new StudentGradeView();
            studentGradeView.setCourseId(studentCourse.getTeacherCourse().getId());
            studentGradeView.setName(studentCourse.getTeacherCourse().getSubject().getName());
            studentGradeView.setTeacherName(studentCourse.getTeacherCourse().getTeacher().getFirstName() + " " +
                                                    studentCourse.getTeacherCourse().getTeacher().getLastName());

            studentGradeViews.add(studentGradeView);
        }
        return studentGradeViews;
    }

    public List<TeacherCourseView> prepareTeacherView(User user) {
        return courseService.getTeacherCoursesList(user);
    }

    private TeacherGradesView getTeacherGradeView(List<TeacherGradesView> gradesViews, User user) {
        for (TeacherGradesView teacherGradesView : gradesViews) {
            if (teacherGradesView.getStudentId() == user.getId()) {
                return teacherGradesView;
            }
        }
        return new TeacherGradesView();
    }

    private boolean hasUser(List<TeacherGradesView> gradesViews, User user) {
        for (TeacherGradesView teacherGradesView : gradesViews) {
            if (teacherGradesView.getStudentId() == user.getId()) {
                return true;
            }
        }
        return false;
    }

    public List<StudentGradeDetailsView> prepareStudentGradesDetails(int courseId) {
        List<StudentGradeDetailsView> StudentGradeDetailsViews = new ArrayList<>();
        TeacherCourse teacherCourse = TeacherCourse.findOne(courseId);
        List<Grade> grades = Grade.findByTeacherCourse(teacherCourse);
        for (Grade grade : grades) {
            StudentGradeDetailsView studentGradeDetailsView = new StudentGradeDetailsView();
            studentGradeDetailsView.setFinalGrade(grade.isFinalGrade());
            studentGradeDetailsView.setTime(grade.getTime().toString());
            studentGradeDetailsView.setValue(grade.getValue());

            StudentGradeDetailsViews.add(studentGradeDetailsView);
        }
        return StudentGradeDetailsViews;
    }

    public List<TeacherGradesView>  prepareTeacherGrades(int courseId) {
        List<TeacherGradesView> gradesViews = new ArrayList<>();
        TeacherCourse teacherCourse = TeacherCourse.findOne(courseId);
        List<StudentCourse> studentCourses = StudentCourse.findByTeacherCourse(teacherCourse);
        for (StudentCourse studentCourse : studentCourses) {
            List<Grade> grades = Grade.findByStudent(studentCourse.getStudent());
            String gradesAsString = grades.stream()
                    .filter(it -> !it.isFinalGrade())
                    .map(it -> String.valueOf(it.getValue()))
                    .collect(Collectors.joining(", "));
            List<String> finalGrades = grades.stream()
                    .filter(Grade::isFinalGrade)
                    .map(it -> String.valueOf(it.getValue()))
                    .collect(Collectors.toList());
            String finalGrade = StringUtils.EMPTY;
            if (CollectionUtils.isNotEmpty(finalGrades)) {
                finalGrade = finalGrades.get(0);
            }
            gradesViews.add(TeacherGradesView.builder()
                    .courseId(studentCourse.getTeacherCourse().getId())
                    .firstName(studentCourse.getStudent().getFirstName())
                    .lastName(studentCourse.getStudent().getLastName())
                    .grades(gradesAsString)
                    .finalGrade(finalGrade)
                    .studentId((int) studentCourse.getStudent().getId())
                    .build());
        }

        return gradesViews;
    }

    private void prepareGradeView(int courseId, List<TeacherGradesView> gradesViews, Grade grade) {
        if (hasUser(gradesViews, grade.getUser())) {
            TeacherGradesView teacherGradesView = getTeacherGradeView(gradesViews, grade.getUser());
            teacherGradesView.setGrades(teacherGradesView.getGrades() + ", " + grade.getValue());
        } else {
            TeacherGradesView teacherGradesView = new TeacherGradesView();
            teacherGradesView.setCourseId(courseId);
            if (grade.isFinalGrade()) {
                teacherGradesView.setFinalGrade(String.valueOf(grade.getValue()));
            }
            teacherGradesView.setFirstName(grade.getUser().getFirstName());
            teacherGradesView.setLastName(grade.getUser().getLastName());
            teacherGradesView.setGrades(String.valueOf(grade.getValue()));
            teacherGradesView.setStudentId((int) grade.getUser().getId());
            gradesViews.add(teacherGradesView);
        }
    }

    public TeacherAddGradeView newCourseByCourseIdAndStudentId(int courseId, int studentId) {
            User user = User.findOne(studentId);
            return TeacherAddGradeView.builder()
                    .courseId(courseId)
                    .studentId(studentId)
                    .studentName(user.getFullName())
                    .build();
    }

    public void updateCourseGrades(int courseId, int studentId, TeacherAddGradeView teacherAddGradeView) {
        TeacherCourse teacherCourse = TeacherCourse.findOne(courseId);
        User student = User.findOne(studentId);
        Grade grade = new Grade();
        if ("YES".equalsIgnoreCase(teacherAddGradeView.getIsFinal()) || "TAK".equalsIgnoreCase(teacherAddGradeView.getIsFinal())) {
            if (Grade.findByStudent(student).stream().anyMatch(Grade::isFinalGrade)) {
                grade = Grade.findByStudent(student).stream().filter(Grade::isFinalGrade).findFirst().get();
            } else {
                grade.setFinalGrade(true);
                grade.setTeacherCourse(teacherCourse);
                grade.setTime(new Timestamp(System.currentTimeMillis()));
                grade.setValue(Integer.valueOf(teacherAddGradeView.getValue()));
                grade.setUser(student);
            }
            grade.setValue(Integer.valueOf(teacherAddGradeView.getValue()));
        } else {
            grade.setFinalGrade(false);
            grade.setTeacherCourse(teacherCourse);
            grade.setTime(new Timestamp(System.currentTimeMillis()));
            grade.setValue(Integer.valueOf(teacherAddGradeView.getValue()));
            grade.setUser(student);
        }
        grade.save();
        prepareTeacherGrades(courseId);
    }
}
