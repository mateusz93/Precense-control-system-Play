package controllers;

import dto.user.ProfileGeneralView;
import dto.user.ProfilePasswordView;
import lombok.val;
import play.data.FormFactory;
import play.mvc.Result;
import play.mvc.Security;
import services.ProfileService;
import views.html.user.profile;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ProfileController extends BaseController {

    private final ProfileService profileService;
    private final FormFactory formFactory;

    @Inject
    public ProfileController(ProfileService service, FormFactory formFactory) {
        this.profileService = service;
        this.formFactory = formFactory;
    }

    @Security.Authenticated
    public Result profile() {
        val form = profileService.prepareGeneralProfileView();
        return ok(profile.render(form, formFactory.form(ProfilePasswordView.class)));
    }

    @Security.Authenticated
    public Result general() {
        val generalView = formFactory.form(ProfileGeneralView.class).bindFromRequest();
        val form = profileService.update(generalView.get());
        return ok(profile.render(wrapErrors(form), formFactory.form(ProfilePasswordView.class)));
    }

    @Security.Authenticated
    public Result password() {
        val passwordView = formFactory.form(ProfilePasswordView.class).bindFromRequest();
        val passwordForm = profileService.updatePassword(passwordView);
        val generalForm = profileService.prepareGeneralProfileView();
        return ok(profile.render(generalForm, wrapErrors(passwordForm)));
    }

}