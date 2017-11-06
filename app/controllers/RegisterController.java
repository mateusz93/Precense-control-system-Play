package controllers;

import dto.security.LoginView;
import dto.security.RegisterView;
import lombok.val;
import play.data.FormFactory;
import play.mvc.Http;
import play.mvc.Result;
import services.RegisterService;
import views.html.security.register;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RegisterController extends BaseController {

    private final RegisterService service;
    private final FormFactory formFactory;

    @Inject
    public RegisterController(RegisterService service, FormFactory formFactory) {
        this.service = service;
        this.formFactory = formFactory;
    }

    public Result register() {
        return ok(register.render(formFactory.form(RegisterView.class), formFactory.form(LoginView.class)));
    }

    public Result registerSubmit() {
        val registerView = formFactory.form(RegisterView.class).bindFromRequest();
        val loginView = formFactory.form(LoginView.class).bindFromRequest();

        if (loginView.hasErrors()){
            wrapErrors(loginView);
            return badRequest(register.render(registerView, loginView));
        }
        else {
            service.registerUser(registerView.bindFromRequest());
            return ok(register.render(registerView, loginView));
        }
    }

    public Result confirmRegistration(String token) {
        return service.confirmRegistration(token);
    }

}