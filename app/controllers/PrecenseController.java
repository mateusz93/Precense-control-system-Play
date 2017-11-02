package controllers;

import models.CourseDateView;
import models.TeacherCourseView;
import play.mvc.Result;
import views.html.checkPrecenses;
import views.html.checkPresence;
import views.html.precensesForTeacher;

import static play.mvc.Results.ok;

public class PrecenseController {
    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        /*Task task = new Task();
        task.id = 123L;
        task.done = true;
        task.dueDate = new Date();
        task.name = "Mateusz";
        task.save();*/
        /*
        Logic checking if logged user is student
         */
        // if Yes
        //return ok(precensesForStudent.render(StudentPrecenseView.findAll());
        // if No
        return ok(precensesForTeacher.render(TeacherCourseView.findAll()));
    }

    public Result info(Long id) {
        //STUDENT precense/precensesInfo
        //return ok(precensesInfo.render(CourseDateView.findAll()));
        //NIESTUDENT precense/checkPrecenses"
        return ok(checkPrecenses.render(CourseDateView.findAll()));
    }

    public Result check(Long id) {
        //TODO change view template to proper
        return ok(checkPrecenses.render(CourseDateView.findAll()));
    }

    public Result update(Long id) {

        //TODO change view template to proper
        return ok(checkPrecenses.render(CourseDateView.findAll()));
    }

}
