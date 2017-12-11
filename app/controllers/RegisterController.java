package controllers;

import dto.security.LoginView;
import dto.security.RegisterView;
import lombok.val;
import models.StudentGroup;
import org.apache.commons.collections4.CollectionUtils;
import play.data.FormFactory;
import play.mvc.Result;
import services.LoginService;
import services.RegisterService;
import views.html.security.login;
import views.html.security.register;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RegisterController extends BaseController {

    private final LoginService loginService;
    private final RegisterService service;
    private final FormFactory formFactory;

    @Inject
    public RegisterController(LoginService loginService, RegisterService service, FormFactory formFactory) {
        this.loginService = loginService;
        this.service = service;
        this.formFactory = formFactory;
    }

    public Result register() {
        return ok(register.render(formFactory.form(RegisterView.class),
                formFactory.form(LoginView.class),
                StudentGroup.findAll()));
    }

    public Result registerSubmit() {
        val registerView = formFactory.form(RegisterView.class).bindFromRequest();
        val loginView = formFactory.form(LoginView.class).bindFromRequest();

        if (registerView.hasErrors()){
            return badRequest(register.render(wrapCustomErrors(registerView), loginView, StudentGroup.findAll()));
        }
        else {
            RegisterView rv= registerView.get();
            loginService.registerValidation(rv);
            if (!CollectionUtils.isEmpty(rv.getMessages())) {
                return badRequest(login.render(BaseController.wrapCustomErrors(registerView), formFactory.form(RegisterView.class), StudentGroup.findAll()));
            }
            service.registerUser(registerView.bindFromRequest());
            return ok(register.render(registerView, loginView, StudentGroup.findAll()));
        }
    }

    public Result confirmRegistration(String token) {
        return service.confirmRegistration(token);
    }

}