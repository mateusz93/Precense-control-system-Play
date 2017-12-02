package controllers;

import dto.course.CourseDateView;
import dto.presence.CheckPresenceView;
import dto.presence.CheckPresenceViewWrapper;
import models.User;
import play.mvc.Result;
import play.mvc.Security;
import services.PresenceService;
import views.html.course.teacherCourseDates;
import views.html.presence.checkPresence;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PresenceController extends BaseController {

    private final PresenceService presenceService;

    @Inject
    public PresenceController(PresenceService presenceService) {
        this.presenceService = presenceService;
    }

    @Security.Authenticated
    public Result index() {
        User user = User.findByLogin(request().attrs().get(Security.USERNAME));
        if("Student".equals(user.getType())) {
            return presenceService.prepareStudentView(user);
        } else {
            return presenceService.prepareTeacherView(user);
        }
    }

    @Security.Authenticated
    public Result courseDates(Integer id) {
        //STUDENT presence/presencesInfo
        //return ok(presencesInfo.render(CourseDateView.findAll()));
        //NIESTUDENT presence/checkPresences")
        return ok(teacherCourseDates.render(CourseDateView.findAll()));
    }

    @Security.Authenticated
    public Result check(Integer id) {
        //TODO change view template to proper
        CheckPresenceViewWrapper checkPresenceViewWrapper = new CheckPresenceViewWrapper();
        checkPresenceViewWrapper.setStudents(CheckPresenceView.findAll());
        return ok(checkPresence.render(0, checkPresenceViewWrapper));
    }

    @Security.Authenticated
    public Result cancel(Integer id) {
        //TODO change view template to proper
        CheckPresenceViewWrapper checkPresenceViewWrapper = new CheckPresenceViewWrapper();
        checkPresenceViewWrapper.setStudents(CheckPresenceView.findAll());
        return ok(checkPresence.render(0, checkPresenceViewWrapper));
    }

    @Security.Authenticated
    public Result update(Integer id) {
        //TODO change view template to proper
        CheckPresenceViewWrapper checkPresenceViewWrapper = new CheckPresenceViewWrapper();
        checkPresenceViewWrapper.setStudents(CheckPresenceView.findAll());
        return ok(checkPresence.render(0, checkPresenceViewWrapper));
    }

}
