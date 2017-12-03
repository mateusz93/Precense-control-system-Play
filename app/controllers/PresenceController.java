package controllers;

import dto.presence.CheckPresenceView;
import dto.presence.CheckPresenceViewWrapper;
import models.User;
import play.mvc.Result;
import play.mvc.Security;
import services.PresenceService;

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
        if("Student".equalsIgnoreCase(user.getType())) {
            return presenceService.prepareStudentView(user);
        } else {
            return presenceService.prepareTeacherView(user);
        }
    }

    @Security.Authenticated
    public Result courseDates(Integer id) {
        User user = User.findByLogin(request().attrs().get(Security.USERNAME));
        return presenceService.prepareCourseDates(id,user);
    }

    @Security.Authenticated
    public Result check(Integer id) {
        return presenceService.preparePresencesList(id);
    }

    @Security.Authenticated
    public Result update(CheckPresenceViewWrapper checkPresenceViewWrapper, Integer id) {
        return presenceService.updateCourseDate(new CheckPresenceView(), id);
    }

}
